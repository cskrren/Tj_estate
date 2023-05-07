package com.rkr.domain.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Package com.rkr.domain.entity
 * @auhter rkr
 * @date 2023/4/30 23:28
 * @description SysRoom:房间信息
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ColumnWidth(20)
public class SysRoom {
    @TableId(type = IdType.AUTO)
    @ExcelProperty("房间编号")
    private String id;
    /**
     * 单元名称
     */
    @ExcelProperty("单元名称")
    private String unitName;
    /**
     * 楼宇名称
     */
    @ExcelProperty("楼宇名称")
    private String buildingName;
    /**
     * 房间朝向
     */
    @ExcelProperty("房间朝向")
    private String direction;
    /**
     * 房间用途
     */
    @ExcelProperty("房间用途")
    private String purpose;
    /**
     * 房间规格
     */
    @ExcelProperty("房间规格")
    private String specifications;
    /**
     * 房间标准
     */
    @ExcelProperty("房间标准")
    private String grade;
    /**
     * 房间建筑面积
     */
    @ExcelProperty("房间建筑面积")
    private String builtupArea;
    /**
     * 使用面积
     */
    @ExcelProperty("使用面积")
    private String useArea;
    /**
     * 是否出售
     */
    @ExcelProperty(value = "是否出售",index = 8)
    private String isSale;


}
