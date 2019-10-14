package com.zxb.product.service;

import com.zxb.product.common.domain.DecreaseStockInput;
import com.zxb.product.common.domain.ProductInfoOutput;
import com.zxb.product.common.util.JsonUtil;
import com.zxb.product.dto.CartDTO;
import com.zxb.product.enums.ProductStatusEnum;
import com.zxb.product.enums.ResultEnum;
import com.zxb.product.exception.ProductException;
import com.zxb.product.model.ProductInfo;
import com.zxb.product.repository.ProductInfoRepository;
import org.assertj.core.util.Lists;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * class
 *
 * @author Mr.zxb
 * @date 2019-09-25 13:52
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Autowired
    private AmqpTemplate amqpTemplate;

    /**
     * 查询上架的商品
     *
     * @return
     */
    @Override
    public List<ProductInfo> findUpAll() {
        return productInfoRepository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public List<ProductInfoOutput> findList(List<String> productIds) {
        return productInfoRepository.findByProductIdIn(productIds).stream()
                .map(e -> {
                    ProductInfoOutput productInfoOutput = new ProductInfoOutput();
                    BeanUtils.copyProperties(e, productInfoOutput);
                    return productInfoOutput;
                }).collect(Collectors.toList());
    }

    /**
     * 扣库存
     *
     * @param decreaseStockInputs
     */
    @Override
    public void decreaseStock(List<DecreaseStockInput> decreaseStockInputs) {
        List<ProductInfoOutput> list = decreaseStockProcess(decreaseStockInputs).stream().map(e -> {
            ProductInfoOutput productInfoOutput = new ProductInfoOutput();
            BeanUtils.copyProperties(e, productInfoOutput);
            return productInfoOutput;
        }).collect(Collectors.toList());
        // 扣库存成功后商品服务发送MQ消息，供订单服务获取MQ消息
        amqpTemplate.convertAndSend("productInfo", JsonUtil.toJson(list));
    }

    @Transactional
    public List<ProductInfo> decreaseStockProcess(List<DecreaseStockInput> decreaseStockInputs) {
        List<ProductInfo> productInfos = Lists.newArrayList();
        for (DecreaseStockInput decreaseStockInput : decreaseStockInputs) {
            Optional<ProductInfo> optionalProductInfo =
                    productInfoRepository.findById(decreaseStockInput.getProductId());
            // 判断商品是否存在
            if (!optionalProductInfo.isPresent()) {
                throw new ProductException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            ProductInfo productInfo = optionalProductInfo.get();

            // 判断库存是否足够
            int stock = productInfo.getProductStock() - decreaseStockInput.getProductQuantity();
            if (stock < 0) {
                throw new ProductException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            productInfo.setProductStock(stock);
            productInfoRepository.save(productInfo);

            productInfos.add(productInfo);
        }
        return productInfos;
    }
}
