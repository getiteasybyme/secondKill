package com.secondKill.controller;

import com.secondKill.commom.Response;
import com.secondKill.pojo.User;
import com.secondKill.service.IOrderService;
import com.secondKill.util.LoginCookieUtil;
import com.secondKill.vo.OrderInformationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class OrderController {

    @Autowired
    private LoginCookieUtil loginCookieUtil;

    @Autowired
    private IOrderService iOrderService;

    /**
     * 查看当前用户订单
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/order",method = RequestMethod.GET)
    public Response queryOrder(HttpServletRequest httpServletRequest){
        User userRedis = loginCookieUtil.loginCheck(httpServletRequest);
        if (userRedis == null){
            return new Response().failure("用户未登录,无法获取当前用户的信息");
        }

        List<OrderInformationVo> voList = iOrderService.getOrder(userRedis.getUserId());
        return new Response().success(voList);
    }
}
