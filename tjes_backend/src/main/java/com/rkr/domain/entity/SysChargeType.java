package com.rkr.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Package com.rkr.domain.entity
 * @auhter rkr
 * @date 2023/4/30 23:28
 * @description SysChargeType:收费类型
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysChargeType {
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 收费类型名称
     */
    private String chargeName;
    /**
     * 收费金额
     */
    private Integer chargeMoney;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
