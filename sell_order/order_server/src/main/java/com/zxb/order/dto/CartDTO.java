package com.zxb.order.dto;

import lombok.Data;

/**
 * class
 *
 * @author Mr.zxb
 * @date 2019-09-25 17:04
 */
@Data
public class CartDTO {

    /**
     * 商品id
     */
    private String productId;

    /**
     * 商品数量
     */
    private Integer productQuantity;

    public CartDTO() {
    }

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
