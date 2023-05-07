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
 * @description SysBuilding:楼宇信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysBuilding {
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    /**
     * 楼宇名称
     */
    private String name;
    /**
     * 楼宇层数
     */
    private Integer layers;
    /**
     * 楼宇高度
     */
    private Integer height;
    /**
     * 楼宇面积
     */
    private Integer area;
    /**
     * 楼宇建成时间
     */
    private Date date;

}
