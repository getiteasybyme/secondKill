package com.secondKill.controller;

import com.secondKill.commom.OrderResponse;
import com.secondKill.commom.Response;
import com.secondKill.pojo.Admin;
import com.secondKill.pojo.Seckill;
import com.secondKill.pojo.User;
import com.secondKill.service.ISecondKillService;
import com.secondKill.util.LoginCookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class SecondKillController {

    @Autowired
    private LoginCookieUtil loginCookieUtil;

    @Autowired
    private ISecondKillService iSecondKillService;

    /**
     * 商品加入秒杀（需要管理员权限）
     * @param httpServletRequest
     * @param seckill
     * @return
     */
    @RequestMapping(value = "/seckil",method = RequestMethod.POST)
    public Response addToSeckill(HttpServletRequest httpServletRequest,
                                 @RequestBody Seckill seckill){
        if (seckill == null){
            return new Response().failure();
        }

        Admin adminRedis = loginCookieUtil.adminLoginCheck(httpServletRequest);
        if (adminRedis == null){
            return  new Response().failure("请获取管理员权限");
        }

        Boolean result = iSecondKillService.addToSeckill(seckill);
        if (result == false){
            return new Response().failure();
        }
        return new Response().success();
    }

    /**
     * 秒杀
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/seckill/{productId}",method = RequestMethod.POST)
    public Response Seckill(HttpServletRequest httpServletRequest,@PathVariable("productId") Long productId){
        User userRedis = loginCookieUtil.loginCheck(httpServletRequest);
        if (userRedis == null){
            return new Response().failure("用户未登录,无法获取当前用户的信息");
        }

        OrderResponse orderResponse = iSecondKillService.secondKill(productId,userRedis.getUserId());
        return orderResponse.getResponse();
    }
}
