package com.zxb.order.service;

import com.zxb.order.client.ProductClient;
import com.zxb.order.dto.CartDTO;
import com.zxb.order.dto.OrderDTO;
import com.zxb.order.enums.OrderStatusEnum;
import com.zxb.order.enums.PayStatusEnum;
import com.zxb.order.enums.ResultEnum;
import com.zxb.order.exception.OrderException;
import com.zxb.order.model.OrderDetail;
import com.zxb.order.model.OrderMaster;
import com.zxb.order.model.ProductInfo;
import com.zxb.order.repository.OrderDetailRepository;
import com.zxb.order.repository.OrderMasterRepository;
import com.zxb.order.util.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 订单实现类 class
 *
 * @author Mr.zxb
 * @date 2019-09-25 17:27
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private ProductClient productClient;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 下单接口
     *
     * @param orderDTO
     * @return
     */
    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        // 异步扣库存业务分析：
        // 1、读取Redis中库存信息
        // 2、收到请求Redis判断库存是否充足，减库存并将新值重新设进Redis
        // 3、订单服务创建订单写入数据库，并发送消息

        // 查询商品信息
        List<String> productIds = orderDTO.getOrderDetails().stream()
                .map(OrderDetail::getProductId)
                .collect(Collectors.toList());
        // 从Redis获取订单信息
        List<ProductInfo> productInfos = productClient.listForOrder(productIds);
//        List<ProductInfo> productInfos = productIds.stream().map(id -> {
//            String key = String.format(ProductInfoReceiver.PRODUCT_STOCK_TEMPLATE, id);
//            String json = redisTemplate.opsForValue().get(key);
//            ProductInfoOutput productInfoOutput = JsonUtil.fromJson(json, ProductInfoOutput.class);
//            ProductInfo productInfo = new ProductInfo();
//            BeanUtils.copyProperties(productInfo, productInfoOutput);
//            return productInfo;
//        }).collect(Collectors.toList());

        String orderId = KeyUtil.getUniqueKey();

        // 计算总价
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
        for (OrderDetail orderDetail : orderDTO.getOrderDetails()) {
            for (ProductInfo productInfo : productInfos) {
                // 单价 * 数量
                orderAmount = orderAmount.add(productInfo.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity())));
                BeanUtils.copyProperties(productInfo, orderDetail);
                orderDetail.setDetailId(KeyUtil.getUniqueKey());
                orderDetail.setOrderId(orderId);

                // 订单入库
                orderDetailRepository.save(orderDetail);
            }
        }
        // 扣库存
        List<CartDTO> cartDTOList = orderDTO.getOrderDetails().stream()
                .map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());

        // feign调远程扣x接口
        productClient.decreaseStock(cartDTOList);

        // 订单入库
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMaster.setCreateTime(new Date());
        orderMaster.setUpdateTime(new Date());

        orderMasterRepository.save(orderMaster);
        return orderDTO;
    }

    /**
     * 订单完成
     * @param orderId
     * @return
     */
    @Override
    @Transactional
    public OrderDTO finish(String orderId) {
        // 先查询订单
        Optional<OrderMaster> optionalOrderMaster = orderMasterRepository.findById(orderId);
        if (!optionalOrderMaster.isPresent()) {
            throw new OrderException(ResultEnum.ORDER_NOT_EXIST);
        }

        // 判断订单状态
        OrderMaster orderMaster = optionalOrderMaster.get();
        if (!OrderStatusEnum.NEW.getCode().equals(orderMaster.getOrderStatus())) {
            throw new OrderException(ResultEnum.ORDER_STATUS_ERROR);
        }

        // 修改订单状态为完结
        orderMaster.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        orderMasterRepository.save(orderMaster);

        List<OrderDetail> orderDetails = orderDetailRepository.findByOrOrderId(orderId);
        if (CollectionUtils.isEmpty(orderDetails)) {
            throw new OrderException(ResultEnum.ORDER_DETAIL_NOT_EXIST);
        }

        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        orderDTO.setOrderDetails(orderDetails);

        return orderDTO;
    }


}
