package com.zxb.order.enums;

import lombok.Getter;

/**
 * enum
 *
 * @author Mr.zxb
 * @date 2019-09-25 12:33
 */
@Getter
public enum PayStatusEnum {

    WAIT(0, "等待支付"),
    SUCCESS(1, "支付成功")
    ;

    private Integer code;
    private String message;

    PayStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
