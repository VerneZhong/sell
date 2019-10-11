package com.zxb.order.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * class
 *
 * @author Mr.zxb
 * @date 2019-09-25 17:02
 */
@Data
public class OrderRequest {
    @NotEmpty(message = "买家姓名必填")
    private String name;
    @NotEmpty(message = "手机号必填")
    private String phone;
    @NotEmpty(message = "地址必填")
    private String address;
    @NotEmpty(message = "openid必填")
    private String openid;
    @NotEmpty(message = "购物车不能为空")
    private String items;
}
