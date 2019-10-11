package com.zxb.order.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * MQ 接收方 class
 *
 * @author Mr.zxb
 * @date 2019-10-11 10:35
 */
@Slf4j
@Component
public class MqReceiver {

    // 1. @RabbitListener(queues = "myQueue")
    // 2. 自动创建队列，下面方式
//    @RabbitListener(queuesToDeclare = @Queue("myQueue"))
    // 3. 自动创建，Exchange和Queue绑定
    @RabbitListener(bindings = @QueueBinding(value = @Queue("myQueue"), exchange = @Exchange("myExchange")))
    public void process(String message) {
        log.info("MqReceiver: {}", message);
    }

    /**
     * 数码供应商服务，接收消息
     * @param message
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("computerQueue")
            , exchange = @Exchange("myOrder")
            // 路由，分组归类
            , key = "computer"))
    public void processComputer(String message) {
        log.info("computer MqReceiver: {}", message);
    }

    /**
     * 水果供应商服务，接收消息
     * @param message
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("fruitQueue")
            , exchange = @Exchange("myOrder")
            // 路由，分组归类
            , key = "fruit"))
    public void processFruit(String message) {
        log.info("fruit MqReceiver: {}", message);
    }
}
