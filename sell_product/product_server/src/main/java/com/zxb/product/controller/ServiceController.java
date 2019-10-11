package com.zxb.product.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * class
 *
 * @author Mr.zxb
 * @date 2019-09-26 11:12
 */
@RestController
public class ServiceController {

    @GetMapping("msg")
    public String msg() {
        return "hello restTemplate msg.";
    }
}
