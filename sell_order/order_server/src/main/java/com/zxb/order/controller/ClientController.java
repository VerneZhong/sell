package com.zxb.order.controller;

import com.zxb.order.client.ProductClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

/**
 * class
 *
 * @author Mr.zxb
 * @date 2019-09-26 11:01
 */
@RestController
@Slf4j
public class ClientController {

    @Autowired
    private ProductClient productClient;

//    @Autowired
//    private RestTemplate restTemplate;

    /**
     * 负载均衡客户端
     */
//    @Autowired
//    private LoadBalancerClient loadBalancerClient;

    @GetMapping("/getProductMsg")
    public String getProductMsg() {
        // 第一种方式，RestTemplate方式，不推荐url不灵活
//        RestTemplate restTemplate = new RestTemplate();
        // 服务地址
//        String serverUrl = "http://192.168.1.119:8080/msg";
//        String response = restTemplate.getForObject(serverUrl, String.class);

        // 第二种方式，服务名称方式代替url调用，restTemplate通过Spring注入使用
        // 该方式需要注入RestTemplate Bean，并且要标注负载均衡@LoadBalanced注解
//        String response = restTemplate.getForObject("http://product/msg", String.class);

        // 第三种方式，通过LoadBalancerClient负载均衡客服端，通过服务名称调用，请求远程服务
//        RestTemplate restTemplate = new RestTemplate();
//        ServiceInstance product = loadBalancerClient.choose("product");
//        URI uri = URI.create(String.format("%s/msg", product.getUri()));
//        String response = restTemplate.getForObject(uri, String.class);

        // 第四种方式，Feign调用
        String response = productClient.productMsg();
        log.info("response={}", response);
        return response;
    }

}
