package com.zxb.order.controller;

import com.zxb.order.dto.OrderDTO;
import com.zxb.order.message.StreamClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * class
 *
 * @author Mr.zxb
 * @date 2019-10-11 14:03
 */
@Slf4j
@RestController
public class SendMessageController {

    @Autowired
    private StreamClient streamClient;

    @GetMapping("/sendMessage")
    public void process() {
        String message = "now Timestamp: " + System.currentTimeMillis();
        streamClient.output().send(MessageBuilder.withPayload(message).build());
    }

    /**
     * 发送OrderDTO对象
     */
    @GetMapping("/sendMessageObject")
    public void processObject() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId("123456");
        streamClient.output().send(MessageBuilder.withPayload(orderDTO).build());
    }
}
