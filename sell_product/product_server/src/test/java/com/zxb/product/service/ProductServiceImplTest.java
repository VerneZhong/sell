package com.zxb.product.service;

import com.zxb.product.ProductApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductServiceImplTest extends ProductApplicationTests {

    @Autowired
    private ProductService productService;

    @Test
    public void findUpAll() {
        System.out.println(productService.findUpAll());
    }

    @Test
    public void findList() {
    }

    @Test
    public void decreaseStock() {
    }

}
