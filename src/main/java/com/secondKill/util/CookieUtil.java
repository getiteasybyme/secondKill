package com.secondKill.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
/***
 * cookie没有进行加密
 */
public class CookieUtil {

    private final static String COOKIE_DOMAIN="192.168.2.1";
    private final static String COOKIE_NAME="user_login_token";
    private final static String COOKIE_ADMIN_NAME="admin_login_token";
    //private final static String COOKIE_NAME="JSESSIONID";

    public static String readLoginToken(HttpServletRequest request){
        Cookie[] cks = request.getCookies();
        if (cks != null){
            for (Cookie ck: cks) {
                log.info("read cookieName:{},cookieValue:{}",ck.getName(),ck.getValue());
                if (StringUtils.equals(ck.getName(),COOKIE_NAME)){
                    log.info("return cookieName:{},cookieValue:{}",ck.getName(),ck.getValue());
                    return ck.getValue();
                }
            }
        }
        return null;
    }

    public static String readAdminLoginToken(HttpServletRequest request){
        Cookie[] cks = request.getCookies();
        if (cks != null){
            for (Cookie ck: cks) {
                log.info("read cookieName:{},cookieValue:{}",ck.getName(),ck.getValue());
                if (StringUtils.equals(ck.getName(),COOKIE_ADMIN_NAME)){
                    log.info("return cookieName:{},cookieValue:{}",ck.getName(),ck.getValue());
                    return ck.getValue();
                }
            }
        }
        return null;
    }

    //domain和setPath("/")间的关系
    //同级域名无法获取到彼此的cookie
    //如果domain设置.happymmall.com
    // 则www.happymmall.com user.happymmall.com products.happymmall.com都能访问到cookie
    //如果设置为www.happymmall.com
    //则同级的user products 都无法访问到www的cookie

    public static void writeLoginToken(HttpServletResponse response,String token){
        Cookie ck = new Cookie(COOKIE_NAME,token);
        ck.setDomain(COOKIE_DOMAIN);
        //代表设置在根目录
        //表示访问的根目录下的文件都能使用这个cookie
        ck.setPath("/");
        //设置不允许通过脚本读取cookie信息，且浏览器不会将cookie发送给第三方
        //一定程度上防止脚本攻击
        //像在京东搜索过的物品，在其他网站时有推送广告，就是脚本读取了cookie，然后进行了推送。
        ck.setHttpOnly(true);
        //单位为秒
        //如果这个maxage不设置的话，cookie就不会写入硬盘而是写在内存。只在当前页面有效
        //如果是-1则代表永久
        ck.setMaxAge(60*60*24*365);
        response.addCookie(ck);
    }

    public static void writeAdminLoginToken(HttpServletResponse response,String token){
        Cookie ck = new Cookie(COOKIE_ADMIN_NAME,token);
        ck.setDomain(COOKIE_DOMAIN);
        //代表设置在根目录
        //表示访问的根目录下的文件都能使用这个cookie
        ck.setPath("/");
        //设置不允许通过脚本读取cookie信息，且浏览器不会将cookie发送给第三方
        //一定程度上防止脚本攻击
        //像在京东搜索过的物品，在其他网站时有推送广告，就是脚本读取了cookie，然后进行了推送。
        ck.setHttpOnly(true);
        //单位为秒
        //如果这个maxage不设置的话，cookie就不会写入硬盘而是写在内存。只在当前页面有效
        //如果是-1则代表永久
        ck.setMaxAge(60*60*24*365);
        response.addCookie(ck);
    }



    //删除token
    public  static void  delLoginToken(HttpServletRequest request,HttpServletResponse response){
        Cookie[] cks = request.getCookies();
        if (cks != null){
            for(Cookie ck : cks){
                if (StringUtils.equals(ck.getName(),COOKIE_ADMIN_NAME)){
                    ck.setDomain(COOKIE_DOMAIN);
                    ck.setPath("/");
                    //设置成0代表删除此cookie
                    ck.setMaxAge(0);
                    log.info("del cookieNMae:{},cookieValue:{}",ck.getName(),ck.getValue());
                    response.addCookie(ck);
                    return;
                }
                if (StringUtils.equals(ck.getName(),COOKIE_NAME)){
                    ck.setDomain(COOKIE_DOMAIN);
                    ck.setPath("/");
                    //设置成0代表删除此cookie
                    ck.setMaxAge(0);
                    log.info("del cookieNMae:{},cookieValue:{}",ck.getName(),ck.getValue());
                    response.addCookie(ck);
                    return;
                }
            }
        }
    }
}
