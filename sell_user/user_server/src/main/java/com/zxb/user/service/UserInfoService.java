package com.zxb.user.service;

import com.zxb.user.domain.UserInfo;

/**
 * interface
 *
 * @author Mr.zxb
 * @date 2019-10-15 16:51
 */
public interface UserInfoService {

    UserInfo findByOpenid(String openid);
}
