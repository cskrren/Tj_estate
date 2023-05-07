package com.rkr.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Package com.rkr.domain.entity
 * @auhter rkr
 * @date 2023/4/30 23:28
 * @description SysCar:车辆信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysCar {
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    /**
     * 车辆名称
     */
    private String name;
    /**
     * 所属用户
     */
    private String user;
    /**
     * 停车位
     */
    private String parking;
    /**
     * 是否在位
     */
    private Boolean status;
    /**
     * 最近时间
     */
    private Date date;
}
