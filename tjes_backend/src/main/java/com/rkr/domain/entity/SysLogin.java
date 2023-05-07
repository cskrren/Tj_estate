package com.rkr.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Package com.rkr.domain.entity
 * @auhter rkr
 * @date 2023/4/30 23:28
 * @description SysLogin:登录信息
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysLogin {
    /**
     * 用户在Session之中存储的key名称
     */
    public static final String LOGIN_USER_SESSION_KEY = "user_permissions";
    /**
     * 登录用户名
     */
    private String userName;
    /**
     * 登录密码
     */
    private String passWord;
    /**
     * 验证码
     */
    private String code;
}




