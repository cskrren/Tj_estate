package com.rkr.service;

import com.google.code.kaptcha.Constants;
import com.rkr.domain.entity.SysLogin;
import com.rkr.domain.entity.SysRegister;
import com.rkr.domain.entity.SysUser;
import com.rkr.service.security.LoginUser;
import com.rkr.utils.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Package com.rkr.service
 * @auhter rkr
 * @date 2023/4/30 23:28
 * @description SysRegisterService:用户注册服务管理
 */

@Service
public class SysRegisterService {

    /**
     * 用户服务
     */
    @Autowired
    private SysUserService sysUserService;

    /**
     * 用户注册
     * @param sysRegister
     * @return boolean
     */
    public SysUser register(SysRegister sysRegister) {
        SysUser sysUser = sysUserService.findByUserName(sysRegister.getUserName());
        if (sysUser != null) {
            return null;
        }
        SysUser newUser = new SysUser().builder()
                .userName(sysRegister.getUserName())
                //随机生成一个名字
                .fullName(sysRegister.getUserName())
                .password(sysRegister.getPassWord())
                .phone(sysRegister.getPhoneNumber())
                .status("0")//未登录状态
                .build();

        String userId = sysUserService.register(newUser);
        System.out.println(userId);
        return sysUserService.findByUserName(sysRegister.getUserName());
    }
}
