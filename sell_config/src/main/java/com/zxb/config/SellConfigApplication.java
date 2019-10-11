package com.zxb.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 统一配置中心引导类
 */
@SpringBootApplication
//@EnableEurekaClient
@EnableDiscoveryClient
@EnableConfigServer
public class SellConfigApplication {

	public static void main(String[] args) {
		SpringApplication.run(SellConfigApplication.class, args);
	}

}
