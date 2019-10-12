package com.zxb.order.message;

import com.zxb.order.dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

/**
 * class
 *
 * @author Mr.zxb
 * @date 2019-10-11 13:50
 */
@Component
@EnableBinding(StreamClient.class)
@Slf4j
public class StreamReceiver {

//    @StreamListener(StreamClient.INPUT)
//    public void process(Object message) {
//        log.info("StreamReceiver 接收者: {}", message);
//    }

    @StreamListener(StreamClient.INPUT)
    @SendTo(StreamClient.INPUT2)
    public String processObject(OrderDTO orderDTO) {
        log.info("StreamReceiver 接收：{}", orderDTO);

        // 转发到其他MQ的消息
        return orderDTO.toString();
    }

    @StreamListener(StreamClient.INPUT2)
    public void processMQMessage(String message) {
        log.info("StreamReceiver2 接收: {}", message);
    }
}
