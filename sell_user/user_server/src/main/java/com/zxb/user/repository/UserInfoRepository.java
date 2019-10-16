package com.zxb.user.repository;

import com.zxb.user.domain.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * interface
 *
 * @author Mr.zxb
 * @date 2019-10-15 16:50
 */
public interface UserInfoRepository extends JpaRepository<UserInfo, String> {

    /**
     * 通过opened查询用户信息
     * @param openid
     * @return
     */
    UserInfo findByOpenid(String openid);
}
