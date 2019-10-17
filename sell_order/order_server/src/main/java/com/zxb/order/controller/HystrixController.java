package com.zxb.order.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

/**
 * class
 *
 * @author Mr.zxb
 * @date 2019-10-17 10:12
 */
@RestController
@DefaultProperties(defaultFallback = "defaultFallback")
public class HystrixController {
    /**
     * 服务降级
     * @return
     */
    @HystrixCommand(
//            fallbackMethod = "fallback",
            commandProperties = {
                    // 请求超时设置，HystrixCommandProperties里获取properties的key
//                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000"),

                    // 服务熔断配置
                    // 熔断器开启设置
                    @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
                    // 在使用统计信息做出打开/关闭决定之前，必须在statisticalWindow中发出的请求数
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
                    // 熔断器后的毫秒数，然后允许重试
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
                    // 必须不使熔断器的“标记”的百分比
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60")
            }
    )
    @GetMapping("/getProductInfoList")
    public String getProductInfoList(@RequestParam("num") Integer num) {
        if (num % 2 == 0) {
            return "success";
        }

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject("http://localhost:8083/product/listForOrder",
                Arrays.asList("157875196366160022"),
                String.class);
    }


    private String fallback() {
        return "网络开小差了，请稍后再试～";
    }

    private String defaultFallback() {
        return "默认提示: 网络开小差了，请稍后再试～";
    }
}
