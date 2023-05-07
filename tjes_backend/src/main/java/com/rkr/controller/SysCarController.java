package com.rkr.controller;

import com.alibaba.fastjson.JSONObject;
import com.rkr.domain.AjaxResult;
import com.rkr.domain.entity.SysBuilding;
import com.rkr.domain.entity.SysCar;
import com.rkr.service.SysBuildingService;
import com.rkr.service.SysCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @Package com.rkr.controller
 * @auhter rkr
 * @date 2023/4/30 15:24
 * @description SysCarController:车辆信息管理
 */

@RestController
@RequestMapping("/system/car")
public class SysCarController {
    @Autowired
    private SysCarService sysCarService;//这里会报错，但是并不影响

    /**
     * 获取所有车辆信息
     * @return AjaxResult
     */
    @GetMapping("/list")
    public AjaxResult getList(){
        return AjaxResult.success(sysCarService.list());
    }

    /**
     * 获取所有车辆名称
     * @return AjaxResult
     */
    @GetMapping("/name/list")
    public AjaxResult getNameList(){
        return AjaxResult.success(sysCarService.nameList());
    }

    /**
     * 保存车辆信息
     * @param sysCar
     * @return AjaxResult
     */
    @PreAuthorize("@ps.hasPermi('system:car:save')")
    @PostMapping("/save")
    public AjaxResult saveBuilding(@RequestBody SysCar sysCar) {
        sysCarService.save(sysCar);
        return AjaxResult.success();
    }

    /**
     * 删除楼栋信息
     * @param jsonObject
     * @return AjaxResult
     */
    @PreAuthorize("@ps.hasPermi('system:car:delete')")
    @PostMapping("/delete")
    public AjaxResult deleteBuilding(@RequestBody JSONObject jsonObject) {
        if(sysCarService.delete(jsonObject.getString("id"))){
            return AjaxResult.success();
        }
        return AjaxResult.error();
    }
}
