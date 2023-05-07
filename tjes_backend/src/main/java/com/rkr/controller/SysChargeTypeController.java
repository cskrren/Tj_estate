package com.rkr.controller;

import com.alibaba.fastjson.JSONObject;
import com.rkr.domain.AjaxResult;
import com.rkr.domain.entity.SysChargeType;
import com.rkr.service.SysChargeTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @Package com.rkr.controller
 * @auhter rkr
 * @date 2023/4/30 15:24
 * @description SysChargeTypeController:收费类型管理
 */

@RestController
@RequestMapping("/system/chargeType")
public class SysChargeTypeController {
    @Autowired
    private SysChargeTypeService sysChargeTypeService;// 这里会报错，但是并不影响

    /**
     * 获取所有收费类型信息
     * @return AjaxResult
     */
    @GetMapping("/list")
    public AjaxResult getList(){
        return AjaxResult.success(sysChargeTypeService.list());
    }

    /**
     * 获取所有收费类型名称
     * @param sysChargeType
     * @return AjaxResult
     */
    @PreAuthorize("@ps.hasPermi('system:chargeType:save')")
    @PostMapping("/save")
    public AjaxResult saveChargeType(@RequestBody SysChargeType sysChargeType) {
        sysChargeTypeService.save(sysChargeType);
        return AjaxResult.success();
    }

    /**
     * 删除收费类型信息
     * @param jsonObject
     * @return AjaxResult
     */
    @PreAuthorize("@ps.hasPermi('system:chargeType:delete')")
    @PostMapping("/delete")
    public AjaxResult deleteChargeType(@RequestBody JSONObject jsonObject) {
        if(sysChargeTypeService.delete(jsonObject.getString("id"))){
            return AjaxResult.success();
        }
        return AjaxResult.error();
    }
}
