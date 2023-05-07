package com.rkr.service;

import com.google.code.kaptcha.Constants;
import com.rkr.domain.entity.SysLogin;
import com.rkr.domain.entity.SysPasswordFind;
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
 * @description SysPasswordFindService :用户找回密码服务管理
 */

@Service
public class SysPasswordFindService {

    /**
     * 用户服务
     */
    @Autowired
    private SysUserService sysUserService;

    /**
     * 用户找回密码
     * @param sysPasswordFind
     * @return boolean
     */
    public SysUser passwordFind(SysPasswordFind sysPasswordFind) {
        SysUser sysUser = sysUserService.findByUserName(sysPasswordFind.getUserName());
        if (sysUser == null) {
            return null;
        }
        sysUserService.resetPwd(sysUser.getId(),sysPasswordFind.getNewPassWord());
        return sysUser;

    }
}
