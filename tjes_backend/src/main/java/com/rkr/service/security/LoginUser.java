package com.rkr.service.security;

import com.rkr.domain.entity.SysUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * @Package com.rkr.service.security
 * @auhter rkr
 * @date 2023/4/30 23:28
 * @description LoginUser:登录用户身份权限
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUser implements UserDetails {

    /**
     * 用户信息
     */
    private SysUser user;

    /**
     * 权限列表
     */
    private List<String> permissions;

    /**
     * 登录IP地址
     */
    private String ipaddr;

    /**
     * 登录地点
     */
    private String loginLocation;

    /**
     * 构造函数
     * @param user
     * @param permissions
     */
    public LoginUser(SysUser user, List<String> permissions)
    {
        this.user = user;
        this.permissions = permissions;
    }

    /**
     * 构造函数
     * @param sysUser
     */
    public LoginUser(SysUser sysUser) {
        this.user = sysUser;
    }

    /**
     * 获取用户权限列表
     * @return 权限列表
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    /**
     * 获取用户密码
     * @return 密码
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * 获取用户账号
     * @return 账号
     */
    @Override
    public String getUsername() {
        return user.getUserName();
    }

    /**
     * 账号是否未过期
     * @return true:未过期 false:已过期
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 账号是否未锁定
     * @return true:未锁定 false:已锁定
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 密码是否未过期
     * @return true:未过期 false:已过期
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 账号是否可用
     * @return true:可用 false:不可用
     */
    @Override
    public boolean isEnabled() {
        return user.getStatus().equals("0");
    }
}
