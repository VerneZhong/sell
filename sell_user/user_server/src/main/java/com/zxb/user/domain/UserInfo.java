package com.zxb.user.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * class
 *
 * @author Mr.zxb
 * @date 2019-10-15 16:49
 */
@Data
@Entity
public class UserInfo {

    @Id
    private String id;
    private String username;
    private String password;
    private String openid;
    private Integer role;
}
