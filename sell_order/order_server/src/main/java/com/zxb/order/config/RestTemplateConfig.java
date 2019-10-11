package com.zxb.order.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * RestTemplate class 注入RestTemplate Bean
 *
 * @author Mr.zxb
 * @date 2019-09-25 17:20
 */
@Component
public class RestTemplateConfig {

    /**
     * 配合RestTemplate远程调用，需要加上负载均衡注解，开启负载均衡
     * @return
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
