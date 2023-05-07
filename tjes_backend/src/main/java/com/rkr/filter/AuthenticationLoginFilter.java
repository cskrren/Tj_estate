package com.rkr.filter;

import com.rkr.service.security.LoginUser;
import com.rkr.utils.RequestUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Package com.rkr.filter
 * @auhter rkr
 * @date 2023/4/30 23:34
 * @description AuthenticationLoginFilter:登录认证过滤器
 */
public class AuthenticationLoginFilter extends BasicAuthenticationFilter {
    public AuthenticationLoginFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    /**
     * 过滤器，判断是否登录
     * @param request
     * @param response
     * @param chain
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        LoginUser loginUser = RequestUtils.getCurrentLoginUser();
        if (loginUser == null) {
            RequestUtils.Forbidden();
            return;
        }
        super.doFilterInternal(request, response, chain);
    }
}
