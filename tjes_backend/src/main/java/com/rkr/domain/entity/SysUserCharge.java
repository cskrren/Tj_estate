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
 * @description SysUserCharge:用户缴费记录
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysUserCharge {
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 费用类型ID
     */
    private Integer chargeTypeId;
    /**
     * 创建时间
     */
    private Date createTime;


}
