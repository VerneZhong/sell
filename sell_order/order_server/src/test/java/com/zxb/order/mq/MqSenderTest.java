package com.zxb.order.mq;

import com.zxb.order.OrderApplicationTests;
import org.junit.Test;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * MQ 发送测试 class
 *
 * @author Mr.zxb
 * @date 2019-10-11 10:40
 */
@Component
public class MqSenderTest extends OrderApplicationTests {

    /**
     * RabbitMQ 操作实例
     */
    @Autowired
    private AmqpTemplate amqpTemplate;

    @Test
    public void send() {
        amqpTemplate.convertAndSend("myQueue", "测试发送内容：" + System.currentTimeMillis());
    }

    @Test
    public void sendOrder() {
        amqpTemplate.convertAndSend("myOrder", "computer", "发送数码供应商内容：MacBook Pro-" + System.currentTimeMillis());
    }

    @Test
    public void sendOrder2() {
        amqpTemplate.convertAndSend("myOrder", "fruit", "发送水果供应商内容：香蕉 -" + System.currentTimeMillis());
    }
}
