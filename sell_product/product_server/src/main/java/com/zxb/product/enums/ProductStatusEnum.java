package com.zxb.product.enums;

import lombok.Getter;

/**
 * enum
 *
 * @author Mr.zxb
 * @date 2019-09-25 12:33
 */
@Getter
public enum ProductStatusEnum {

    UP(0, "在架"),
    DOWN(1, "下架")
    ;

    private Integer code;
    private String message;

    ProductStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
