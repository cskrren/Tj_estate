package com.rkr.utils;

import com.alibaba.fastjson.JSON;
import com.rkr.domain.AjaxResult;
import com.rkr.domain.constant.HttpStatus;
import com.rkr.domain.entity.SysLogin;
import com.rkr.service.security.LoginUser;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Package com.rkr.utils
 * @auhter rkr
 * @date 2023/4/30 23:28
 * @description RequestUtils:请求工具类
 */
@Component
public class RequestUtils {

    /**
     * 获取当前线程的Request对象
     * @return HttpServletRequest
     */
    public static HttpServletRequest getCurrentRequest() {
        return getServletRequestAttributes().getRequest();
    }

    /**
     * 获取当前线程的Response对象
     * @return HttpServletResponse
     */
    public static HttpServletResponse getCurrentResponse() {
        return getServletRequestAttributes().getResponse();
    }

    /**
     * 获取当前线程的ServletRequestAttributes对象
     * @return ServletRequestAttributes
     */
    public static ServletRequestAttributes getServletRequestAttributes(){
        return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    }

    /**
     * 获取当前线程的Session对象
     * @return HttpSession
     */
    public static void setCurrentSessionAttribute(String key,Object val){
        getCurrentRequest().getSession().setAttribute(key,val);
    }

    /**
     * 获取当前线程的Session对象
     * @return HttpSession
     */
    public static void removeCurrentSessionAttribute(String key){
        getCurrentRequest().getSession().removeAttribute(key);
    }

    /**
     * 获取当前线程的Session对象
     * @return HttpSession
     */
    public static Object getCurrentSessionAttribute(String key){
        return getCurrentRequest().getSession().getAttribute(key);
    }

    /**
     * 获取当前线程的Session对象
     * @return HttpSession
     */
    public static LoginUser getCurrentLoginUser(){
        return (LoginUser)getCurrentSessionAttribute(SysLogin.LOGIN_USER_SESSION_KEY);
    }

    /**
     * 删除当前Session对象
     * @return HttpSession
     */
    public static void invalidate(){
        getCurrentRequest().getSession().invalidate();
    }

    /**
     * 获取当前线程的Session对象
     * @return HttpSession
     */
    public static void NoPeri(){
        ServletRequestAttributes attributes = getServletRequestAttributes();
        String msg = StringUtils.format("请求访问：{}，认证失败，无法访问系统资源",
                attributes.getRequest().getRequestURI());
        ServletUtils.renderString(attributes.getResponse(), JSON.toJSONString(AjaxResult.error(HttpStatus.UNAUTHORIZED, msg)));
    }

    /**
     * 获取当前线程的Session对象
     * @return HttpSession
     */
    public static void Forbidden() {
        ServletUtils.renderString(getCurrentResponse(), JSON.toJSONString(AjaxResult.forbidden()));
    }
}
