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
 * @description SysRepair:报修单
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysRepair {
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 用户报修单标题
     */
    private String title;
    /**
     * 用户联系方式
     */
    private String phone;
    /**
     * 用户住址
     */
    private String address;
    /**
     * 申请时间
     */
    private Date date;
    /**
     * 用户报修单详细内容
     */
    private String text;
    /**
     * 管理员是否审核
     */
    private Integer isExamine;
    /**
     * 管理员处理回执数据
     */
    private String examineData;
}
