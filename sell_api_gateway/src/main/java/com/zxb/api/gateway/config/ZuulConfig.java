package com.zxb.api.gateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.stereotype.Component;

/**
 * class
 *
 * @author Mr.zxb
 * @date 2019-10-15 09:40
 */
@Component
public class ZuulConfig {

//    @ConfigurationProperties(prefix = "zuul")
    @RefreshScope
    public ZuulProperties zuulProperties() {
        return new ZuulProperties();
    }
}
