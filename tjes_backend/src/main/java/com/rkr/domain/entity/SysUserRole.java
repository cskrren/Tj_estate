package com.rkr.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Package com.rkr.domain.entity
 * @auhter rkr
 * @date 2023/4/30 23:28
 * @description SysUserRole:用户角色关联表
 */

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class SysUserRole {
    /**
     * 用户ID
     */
    @TableId
    private String userId;
    /**
     * 角色ID
     */
    private Integer roleId;
}
