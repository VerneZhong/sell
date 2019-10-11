package com.zxb.order.exception;

import com.zxb.order.enums.ResultEnum;

/**
 * class
 *
 * @author Mr.zxb
 * @date 2019-09-25 11:21
 */
public class OrderException extends RuntimeException {

    private Integer code;

    public OrderException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public OrderException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }
}
