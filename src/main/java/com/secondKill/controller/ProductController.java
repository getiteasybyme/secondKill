package com.secondKill.controller;

import com.secondKill.commom.Response;
import com.secondKill.pojo.Admin;
import com.secondKill.pojo.Product;
import com.secondKill.pojo.User;
import com.secondKill.service.IProductService;
import com.secondKill.util.LoginCookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private LoginCookieUtil loginCookieUtil;

    @Autowired
    private IProductService iProductService;

    /**
     * 查看可秒杀的商品列表
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/product",method = RequestMethod.GET)
    public Response queryProduct(HttpServletRequest httpServletRequest){
        User userRedis = loginCookieUtil.loginCheck(httpServletRequest);
        Admin adminRedis = loginCookieUtil.adminLoginCheck(httpServletRequest);
        if (userRedis == null && adminRedis ==null){
            return new Response().failure("用户未登录,无法获取当前商品的信息");
        }

       return new Response().success(iProductService.queryProductList());
    }

    /**
     * 新增商品（管理员权限）
     * @param httpServletRequest
     * @param product
     * @return
     */
    @RequestMapping(value = "/product",method = RequestMethod.POST)
    public Response newProduct(HttpServletRequest httpServletRequest,
                               @RequestBody Product product){
        if (product == null){
            return new Response().failure("参数异常");
        }

        Admin adminRedis = loginCookieUtil.adminLoginCheck(httpServletRequest);
        if (adminRedis == null){
            return  new Response().failure("请获取管理员权限");
        }

        Boolean result = iProductService.newProduct(product);
        if (result == false){
            return new Response().failure("新增失败");
        }

        return new Response().success();
    }

    /**
     * 删除商品（管理员权限）
     * @param httpServletRequest
     * @param productId
     * @return
     */
    @RequestMapping(value = "/product/{productId}",method = RequestMethod.DELETE)
    public Response deleteProduct(HttpServletRequest httpServletRequest,
                                  @PathVariable("productId") Long productId){
        if (productId == null){
            return new Response().failure("参数异常");
        }

        Admin adminRedis = loginCookieUtil.adminLoginCheck(httpServletRequest);
        if (adminRedis == null){
            return  new Response().failure("请获取管理员权限");
        }

        Boolean result = iProductService.deleteProduct(productId);
        if (result == false){
            return new Response().failure("删除失败");
        }

        return new Response().success();
    }
}
