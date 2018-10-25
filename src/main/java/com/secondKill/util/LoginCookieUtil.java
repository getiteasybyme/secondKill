package com.secondKill.util;

import com.secondKill.pojo.Admin;
import com.secondKill.pojo.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Component
public class LoginCookieUtil {
    /**
     * 用户登录cookie校验
     */
    public User loginCheck(HttpServletRequest httpServletRequest){
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        if (StringUtils.isEmpty(loginToken)){
            return null;
        }

        String userJsonStr = RedisPoolUtil.get(loginToken);
        return JsonUtil.string2Obj(userJsonStr, User.class);
    }

    /**
     * 管理员登录cookie校验
     */
    public Admin adminLoginCheck(HttpServletRequest httpServletRequest){
        String loginToken = CookieUtil.readAdminLoginToken(httpServletRequest);
        if (StringUtils.isEmpty(loginToken)){
            return null;
        }

        String userJsonStr = RedisPoolUtil.get(loginToken);
        return JsonUtil.string2Obj(userJsonStr, Admin.class);
    }
}
