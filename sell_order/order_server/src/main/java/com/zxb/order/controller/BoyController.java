package com.zxb.order.controller;

import com.zxb.order.config.BoyConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * class
 *
 * 手动刷新配置：
 * github Webhook http://4quxbq.natappfree.cc/monitor
 * curl -v -X POST "http://192.168.1.119:8082/actuator/bus-refresh"
 * curl -v -X POST "http://4quxbq.natappfree.cc/actuator/bus-refresh"
 *
 * @author Mr.zxb
 * @date 2019-10-09 09:47
 */
@RestController
public class BoyController {

    @Autowired
    private BoyConfig boyConfig;

    @GetMapping("/boy/print")
    public String print() {
        return "name: " + boyConfig.getName() + ", age: " + boyConfig.getAge();
    }
}
