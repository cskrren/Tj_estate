package com.rkr.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUserInfo {
    @TableId
    private String userId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 用户性别
     */
    private String userGender;
    /**
     * 用户年龄
     */
    private String userAge;
    /**
     * 用户手机号
     */
    private String userPhone;
    /**
     * 用户邮箱
     */
    private String userEmail;
    /**
     * 工作地点
     */
    private String userWorkPlace;
    /**
     * 用户头像
     */
    private String userAvatar;
    /**
     * 用户状态
     */
    private Integer online;
}
