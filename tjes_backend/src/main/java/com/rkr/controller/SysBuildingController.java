package com.rkr.controller;

import com.alibaba.fastjson.JSONObject;
import com.rkr.domain.AjaxResult;
import com.rkr.domain.entity.SysBuilding;
import com.rkr.service.SysBuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @Package com.rkr.controller
 * @auhter rkr
 * @date 2023/4/30 15:24
 * @description SysBuildingController:楼栋信息管理
 */

@RestController
@RequestMapping("/system/building")
public class SysBuildingController {
    @Autowired
    private SysBuildingService sysBuildingService;//这里会报错，但是并不影响

    /**
     * 获取所有楼栋信息
     * @return AjaxResult
     */
    @GetMapping("/list")
    public AjaxResult getList(){
        return AjaxResult.success(sysBuildingService.list());
    }

    /**
     * 获取所有楼栋名称
     * @return AjaxResult
     */
    @GetMapping("/name/list")
    public AjaxResult getNameList(){
        return AjaxResult.success(sysBuildingService.nameList());
    }

    /**
     * 保存楼栋信息
     * @param sysBuilding
     * @return AjaxResult
     */
    @PreAuthorize("@ps.hasPermi('system:building:save')")
    @PostMapping("/save")
    public AjaxResult saveBuilding(@RequestBody SysBuilding sysBuilding) {
        sysBuildingService.save(sysBuilding);
        return AjaxResult.success();
    }

    /**
     * 删除楼栋信息
     * @param jsonObject
     * @return AjaxResult
     */
    @PreAuthorize("@ps.hasPermi('system:building:delete')")
    @PostMapping("/delete")
    public AjaxResult deleteBuilding(@RequestBody JSONObject jsonObject) {
        if(sysBuildingService.delete(jsonObject.getString("id"))){
            return AjaxResult.success();
        }
        return AjaxResult.error();
    }
}
