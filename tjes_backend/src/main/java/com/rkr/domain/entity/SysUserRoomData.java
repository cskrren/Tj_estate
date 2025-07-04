package com.rkr.domain.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
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
 * @description SysUserRoom:用户信息
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ColumnWidth(20)
public class SysUserRoomData {
    @TableId(type = IdType.ASSIGN_UUID)
    @ExcelIgnore
    private String id;
    /**
     * 用户ID
     */
    @ExcelIgnore
    private String userId;
    /**
     * 用户名
     */
    @ExcelProperty("用户名称")
    private String userName;
    /**
     * 真实姓名
     */
    @ExcelProperty("真实姓名")
    private String fullName;
    /**
     * 手机号
     */
    @ExcelProperty("手机号")
    private String phone;

    /**
     * 楼宇名称
     */
    @ExcelProperty("楼宇名称")
    private String buildingName;
    /**
     * 单元名称
     */
    @ExcelProperty("单元名称")
    private String unitName;
    /**
     * 房间ID
     */
    @ExcelProperty("房间编号")
    private Integer roomId;

}
