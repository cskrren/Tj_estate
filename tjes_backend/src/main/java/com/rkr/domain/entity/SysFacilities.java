package com.rkr.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Package com.rkr.domain.entity
 * @auhter rkr
 * @date 2023/4/30 23:28
 * @description SysFacilities:设施信息
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysFacilities {
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    /**
     * 设施名称
     */
    private String name;
    /**
     * 设施类型
     */
    private String type;
    /**
     * 设置负责人
     */
    private String chargePerson;
    /**
     * 设施联系人
     */
    private String contactPerson;
    /**
     * 设施联系电话
     */
    private String phone;
    /**
     * 设置描述
     */
    private String descri;


}
