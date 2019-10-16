package com.zxb.user.controller;

import com.mysql.cj.PreparedQuery;
import com.zxb.user.constant.CookieConstant;
import com.zxb.user.constant.RedisConstant;
import com.zxb.user.domain.UserInfo;
import com.zxb.user.enums.ResultEnum;
import com.zxb.user.enums.RoleEnum;
import com.zxb.user.service.UserInfoService;
import com.zxb.user.util.CookieUtil;
import com.zxb.user.vo.ResultVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * class
 *
 * @author Mr.zxb
 * @date 2019-10-15 16:52
 */
@RestController
@RequestMapping("/user")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 买家登录
     *
     * @param openid
     * @param response
     * @return
     */
    @GetMapping("/buyer")
    public ResultVO buyer(@RequestParam("openid") String openid, HttpServletResponse response) {

        ResultVO resultVO = permissionCheck(openid, RoleEnum.BUYER);
        if (resultVO != null) {
            return resultVO;
        }
        // cookie里设置openid=abc
        CookieUtil.set(response, CookieConstant.OPENID, openid, CookieConstant.EXPIRE);

        return ResultVO.success();
    }

    private ResultVO permissionCheck(String openid, RoleEnum roleEnum) {
        // 判断用户是否存在
        UserInfo userInfo = userInfoService.findByOpenid(openid);
        if (userInfo == null) {
            return ResultVO.error(ResultEnum.LOGIN_FAIL);
        }
        // 角色判断
        if (!roleEnum.getCode().equals(userInfo.getRole())) {
            return ResultVO.error(ResultEnum.ROLE_FAIL);
        }
        return null;
    }

    /**
     * @param openid
     * @param response
     * @return
     */
    @GetMapping("/seller")
    public ResultVO seller(@RequestParam("openid") String openid,
                           HttpServletRequest request,
                           HttpServletResponse response) {

        // 从cookie和redis里判断是否已登陆
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if (cookie != null &&
                !StringUtils.isEmpty(redisTemplate.opsForValue()
                        .get(String.format(RedisConstant.TOKEN_TEMPLATE, cookie.getValue())))) {
            return ResultVO.success();
        }

        // 权限判断
        ResultVO resultVO = permissionCheck(openid, RoleEnum.SELLER);
        if (resultVO != null) {
            return resultVO;
        }

        String uuid = UUID.randomUUID().toString();
        // 设置redis
        redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_TEMPLATE, uuid),
                openid,
                CookieConstant.EXPIRE,
                TimeUnit.SECONDS);

        // 存到cookie
        CookieUtil.set(response, CookieConstant.TOKEN, uuid, CookieConstant.EXPIRE);

        return ResultVO.success();
    }
}
