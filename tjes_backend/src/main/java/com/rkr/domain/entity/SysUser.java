package com.rkr.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Package com.rkr.domain.entity
 * @auhter rkr
 * @date 2023/4/30 23:28
 * @description SysUser:用户
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUser {
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 真实姓名
     */
    private String fullName;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 用户状态(0正常 1停用)
     */
    private String status;
    /**
     * 用户手机号
     */
    private String phone;
    /**
     * 用户最后登录IP
     */
    private String loginIp;
    /**
     * 用户最后登录时间
     */
    private Date loginDate;

}
