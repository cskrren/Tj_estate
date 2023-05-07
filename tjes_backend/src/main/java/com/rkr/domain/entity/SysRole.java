package com.rkr.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Package com.rkr.domain.entity
 * @auhter rkr
 * @date 2023/4/30 23:28
 * @description SysRole:角色信息
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysRole {
    private Integer id;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 角色Key
     */
    private String roleKey;


}
