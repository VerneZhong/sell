package com.zxb.user.enums;

/**
 * enum
 *
 * @author Mr.zxb
 * @date 2019-10-15 17:16
 */
public enum RoleEnum {

    /**
     *
     */
    BUYER(1, "买家"),
    /**
     *
     */
    SELLER(2, "卖家")
    ;

    private Integer code;
    private String message;

    RoleEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
