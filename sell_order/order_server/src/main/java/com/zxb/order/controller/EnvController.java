package com.zxb.order.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * class
 *
 * @author Mr.zxb
 * @date 2019-09-27 17:18
 */
@RestController
@RequestMapping("/env")
// 刷新配置注解
@RefreshScope
public class EnvController {

    @Value("${env}")
    private String env;

    @GetMapping("getEnv")
    public String getEnv() {
        return env;
    }
}
