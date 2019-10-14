package com.zxb.order.enums;

import lombok.Getter;

/**
 * enum
 *
 * @author Mr.zxb
 * @date 2019-09-25 12:33
 */
@Getter
public enum OrderStatusEnum {

    NEW(0, "新订单"),
    CREATED(1, "创建中"),
    FINISHED(2, "完结"),
    CANCEL(3, "取消"),
    ;

    private Integer code;
    private String message;

    OrderStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
