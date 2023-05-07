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
 * @description SysJob:定时任务
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysJob {
    @TableId(type = IdType.AUTO)
    private String id;
    /**
     * 任务名称
     */
    private String name;
    /**
     * 任务组名
     */
    private String groupName;
    /**
     * 任务执行计划
     */
    private String cron;
    /**
     * 任务目标执行类
     */
    private String classPath;
    /**
     * 任务状态(0 开启,1 暂停)
     */
    private String status;
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
