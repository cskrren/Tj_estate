package com.rkr.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Package com.rkr.domain.entity
 * @auhter rkr
 * @date 2023/4/30 23:28
 * @description SysUserRoom:用户房产信息
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUserRoom {
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 房间ID
     */
    private String roomId;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private String createTime;
    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateTime;



}
