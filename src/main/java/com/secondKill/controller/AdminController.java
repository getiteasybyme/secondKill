package com.secondKill.controller;

import com.secondKill.commom.Const;
import com.secondKill.commom.Response;
import com.secondKill.pojo.Admin;
import com.secondKill.pojo.User;
import com.secondKill.service.IAdminService;
import com.secondKill.serviceImpl.AdminServiceImpl;
import com.secondKill.util.CookieUtil;
import com.secondKill.util.JsonUtil;
import com.secondKill.util.RedisPoolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
public class AdminController {

    @Autowired
    private IAdminService adminService;

    /**
     * 管理员登录
     * @param request
     * @param session
     * @param httpServletResponse
     * @param admin
     * @return
     */
    @RequestMapping(value = "/admin/login",method = RequestMethod.POST)
    public Response adminLogin(HttpServletRequest request, HttpSession session,
                               HttpServletResponse httpServletResponse,
                               @RequestBody Admin admin) {
        if (admin.getAdminId()== null || admin.getPassword() == null) {
            return new Response().failure();
        }
        Admin resultAdmin = adminService.login(admin.getAdminId(), admin.getPassword());
        if (resultAdmin == null) {
            return new Response().failure();
        }
        String  string  = new String("Administrator");
        CookieUtil.writeAdminLoginToken(httpServletResponse,string );
        RedisPoolUtil.setex(string, JsonUtil.obj2String(resultAdmin),
                Const.RedisCacheExTime.REDIS_SESSION_EXTIME);
        return new Response().success("admin");
    }
}

