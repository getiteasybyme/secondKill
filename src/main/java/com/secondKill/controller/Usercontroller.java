package com.secondKill.controller;

import com.secondKill.commom.Const;
import com.secondKill.commom.Response;
import com.secondKill.pojo.User;
import com.secondKill.service.IUserService;
import com.secondKill.util.CookieUtil;
import com.secondKill.util.JsonUtil;
import com.secondKill.util.LoginCookieUtil;
import com.secondKill.util.RedisPoolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.HashSet;

@RestController
public class Usercontroller {

    @Autowired
    private LoginCookieUtil loginCookieUtil;

    @Autowired
    private IUserService iUserService;

    /**
     * 登录
     * @param httpServletRequest
     * @param session
     * @param httpServletResponse
     * @param user
     * @return
     */
    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public Response login(HttpServletRequest httpServletRequest, HttpSession session, HttpServletResponse httpServletResponse,
                          @RequestBody User user) {
        if (user.getUserName()== null || user.getPassword() == null) {
            return new Response().failure();
        }
        User resultUser = iUserService.login(user.getUserName(), user.getPassword());
        if (resultUser == null) {
            return new Response().failure();
        }

        CookieUtil.writeLoginToken(httpServletResponse, session.getId());
        RedisPoolUtil.setex(session.getId(), JsonUtil.obj2String(resultUser), Const.RedisCacheExTime.REDIS_SESSION_EXTIME);
        return new Response().success("user");
    }

    /**
     * 注册
     * @param user
     * @return
     */
    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
    public Response register(@RequestBody User user) {
        if (user == null) {
            return new Response().failure();
        }

        Boolean result = iUserService.register(user);
        if (result == false) {
            return new Response().failure();
        }

        return new Response().success();
    }

    /**
     * 忘记密码
     * @param user
     * @return
     */
    @RequestMapping(value = "/user/password", method = RequestMethod.POST)
    public Response resetPassword(@RequestBody User user) {
        if (user.getUserName() == null || user.getEmail() == null || user.getTelephone() == null || user.getPassword() == null) {
            return new Response().failure();
        }

        Boolean resultValue = iUserService.resetPassword(user);
        if (resultValue == false) {
            return new Response().failure();
        }

        return new Response().success();
    }

    /**
     * 修改密码
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/user/password", method = RequestMethod.PUT)
    public Response changePassword(HttpServletRequest httpServletRequest,@RequestBody User userRequest) {
        if (userRequest.getPassword() == null){
            return new Response().failure();
        }

        User userRedis = loginCookieUtil.loginCheck(httpServletRequest);
        if (userRedis == null){
            return new Response().failure("用户未登录,无法获取当前用户的信息");
        }

        if (userRedis.getUserId() == null || userRedis.getPassword()== null){
            return new Response().failure();
        }

        Boolean resultValue = iUserService.changePassword(userRedis.getUserId(),userRequest.getPassword());
        if (resultValue == false){
            return new Response().failure();
        }

        return new Response().success();
    }

    /**
     * 查看个人信息
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/user/information",method = RequestMethod.GET)
    public Response getInformation(HttpServletRequest httpServletRequest) {
        User userRedis = loginCookieUtil.loginCheck(httpServletRequest);
        if (userRedis == null){
            return new Response().failure("用户未登录,无法获取当前用户的信息");
        }
        userRedis.setPassword(null);
        return new Response().success(userRedis);
    }

    /**
     * 更改个人信息
     * @param user
     * @return
     */
    @RequestMapping(value = "/user/information",method = RequestMethod.PUT)
    public Response changeInformation(HttpServletRequest httpServletRequest,@RequestBody User user){
        if (user.getPassword() != null){
            return new Response().failure();
        }
        User userRedis = loginCookieUtil.loginCheck(httpServletRequest);

        if (user == null){
            return new Response().failure("用户未登录,无法获取当前用户的信息");
        }

        user.setUserId(userRedis.getUserId());
        user.setPassword(userRedis.getPassword());
        if (user.getUserId() == null){
            return new Response().failure();
        }

        Integer resultValue = iUserService.changeInformation(user);
        if (resultValue <= 0){
            return new Response().failure();
        }
        return new Response().success();
    }

    /**
     * 注销
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/user/logout",method = RequestMethod.DELETE)
    public Response logout(HttpServletRequest request,HttpServletResponse response){
        String loginToken = CookieUtil.readLoginToken(request);
        CookieUtil.delLoginToken(request,response);
        RedisPoolUtil.del(loginToken);
        return new Response().success();
    }
}
