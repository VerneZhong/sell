package com.zxb.order.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * class
 *
 * @author Mr.zxb
 * @date 2019-10-09 09:46
 */
@Data
@Component
@ConfigurationProperties("boy")
@RefreshScope
public class BoyConfig {

    private String name;
    private Integer age;
}
