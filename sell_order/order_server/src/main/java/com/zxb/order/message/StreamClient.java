package com.zxb.order.message;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * Spring Cloud Stream 示例 interface
 *
 * @author Mr.zxb
 * @date 2019-10-11 13:48
 */
public interface StreamClient {

    String INPUT = "myMessage";

    String INPUT2 = "myMessage2";

    @Input
    SubscribableChannel input();

    @Output(StreamClient.INPUT)
    MessageChannel output();

    @Input
    SubscribableChannel input2();

    @Output(StreamClient.INPUT2)
    MessageChannel output2();
}
