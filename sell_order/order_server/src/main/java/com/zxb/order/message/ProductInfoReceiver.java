package com.zxb.order.message;

import com.fasterxml.jackson.databind.JavaType;
import com.zxb.common.product.domain.ProductInfoOutput;
import com.zxb.common.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * class
 *
 * @author Mr.zxb
 * @date 2019-10-12 13:55
 */
@Component
@Slf4j
public class ProductInfoReceiver {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public static final String PRODUCT_STOCK_TEMPLATE = "product_stock_%s";

    /**
     * 监听商品服务发来的MQ消息
     * @param message
     */
    @RabbitListener(queuesToDeclare = @Queue("productInfo"))
    public void process(String message) {
        // message -> ProductInfoOutput
        JavaType javaType = JsonUtil.createJavaType(List.class, ProductInfoOutput.class);
        List<ProductInfoOutput> productInfoOutputs = JsonUtil.fromJson(message, javaType);
        log.info("从队列[{}]接收到商品服务扣库存成功消息：{}", "productInfo", JsonUtil.toJson(productInfoOutputs));

        for (ProductInfoOutput productInfoOutput : productInfoOutputs) {
            // 存储到redis
            stringRedisTemplate.opsForValue().set(String.format(PRODUCT_STOCK_TEMPLATE,
                    productInfoOutput.getProductId()),
                    JsonUtil.toJson(productInfoOutput));
        }
    }
}
