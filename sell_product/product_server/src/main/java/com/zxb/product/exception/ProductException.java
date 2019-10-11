package com.zxb.product.exception;

import com.zxb.product.enums.ResultEnum;

/**
 * class
 *
 * @author Mr.zxb
 * @date 2019-09-25 11:21
 */
public class ProductException extends RuntimeException {

    private Integer code;

    public ProductException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public ProductException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }
}
