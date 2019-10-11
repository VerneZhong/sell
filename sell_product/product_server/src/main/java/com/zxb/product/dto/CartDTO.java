package com.zxb.product.dto;

import lombok.Data;

/**
 * class
 *
 * @author Mr.zxb
 * @date 2019-09-25 12:35
 */
@Data
public class CartDTO {

    /**
     * 商品id
     */
    private String productId;

    /**
     *  商品数量
     */
    private Integer productQuantity;
}
