package com.rkr.handler;

import java.io.IOException;
import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rkr.utils.RequestUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;


/**
 * @Package com.rkr.handler
 * @auhter rkr
 * @date 2023/4/30 23:28
 * @description
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint, Serializable {

    /**
     * default值
     */
    private static final long serialVersionUID = -8970718410437077606L;

    /**
     * 未登录或token失效时访问接口时，自定义的返回结果
     * @param request
     * @param response
     * @param e
     * @throws IOException
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
            throws IOException {

        RequestUtils.NoPeri();
    }
}
