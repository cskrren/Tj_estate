package com.rkr.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Package com.rkr.domain.entity
 * @auhter rkr
 * @date 2023/4/30 23:28
 * @description SysRegister:注册信息
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysPasswordFind {
    /**
     * 注册用户名
     */
    private String userName;
    /**
     * 注册密码
     */
    private String newPassWord;
}
