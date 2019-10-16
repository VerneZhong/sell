package com.zxb.user.enums;

/**
 * class
 *
 * @author Mr.zxb
 * @date 2019-10-15 17:11
 */
public enum ResultEnum {
    /**
     * 登录失败
     */
    LOGIN_FAIL(1, "登录失败"),
    ROLE_FAIL(1, "角色权限有误")
    ;

    private Integer code;
    private String message;

    ResultEnum(Integer code, String message) {
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
