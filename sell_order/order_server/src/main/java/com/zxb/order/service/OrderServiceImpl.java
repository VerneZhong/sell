package com.zxb.order.service;

import com.zxb.order.client.ProductClient;
import com.zxb.order.dto.CartDTO;
import com.zxb.order.dto.OrderDTO;
import com.zxb.order.enums.OrderStatusEnum;
import com.zxb.order.enums.PayStatusEnum;
import com.zxb.order.model.OrderDetail;
import com.zxb.order.model.OrderMaster;
import com.zxb.order.model.ProductInfo;
import com.zxb.order.repository.OrderDetailRepository;
import com.zxb.order.repository.OrderMasterRepository;
import com.zxb.order.util.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
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

    /**
     * 下单接口
     *
     * @param orderDTO
     * @return
     */
    @Override
    public OrderDTO create(OrderDTO orderDTO) {
        // 查询商品信息
        List<String> productIds = orderDTO.getOrderDetails().stream()
                .map(OrderDetail::getProductId)
                .collect(Collectors.toList());

        List<ProductInfo> productInfos = productClient.listForOrder(productIds);

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
}
