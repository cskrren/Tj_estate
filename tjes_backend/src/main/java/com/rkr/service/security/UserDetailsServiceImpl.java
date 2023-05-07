package com.rkr.service.security;

import com.rkr.domain.entity.SysUser;
import com.rkr.service.SysUserService;
import com.rkr.utils.IpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @Package com.rkr.service.security
 * @auhter rkr
 * @date 2023/4/30 23:28
 * @description UserDetailsServiceImpl:用户登录认证信息查询
 */

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    /**
     * 用户服务
     */
    @Autowired
    private SysUserService sysUserService;

    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = sysUserService.loadUserByUsername(username);
        if(sysUser == null){
            return null;
        }
        String ipAddr = IpUtils.getIpAddr();
        return new LoginUser(sysUser);
    }
}
