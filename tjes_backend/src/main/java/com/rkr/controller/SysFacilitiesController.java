package com.rkr.controller;

import com.alibaba.fastjson.JSONObject;
import com.rkr.domain.AjaxResult;
import com.rkr.domain.entity.SysFacilities;
import com.rkr.service.SysFacilitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @Package com.rkr.controller
 * @auhter rkr
 * @date 2023/4/30 15:24
 * @description SysFacilitiesController:设施信息管理
 */

@RestController
@RequestMapping("/system/facilities")
public class SysFacilitiesController {
    @Autowired
    private SysFacilitiesService sysFacilitiesService;// 这里会报错，但是并不影响

    /**
     * 获取所有设施信息
     * @return AjaxResult
     */
    @GetMapping("/list")
    public AjaxResult getList(){
        return AjaxResult.success(sysFacilitiesService.list());
    }

    /**
     * 获取所有设施名称
     * @param sysFacilities
     * @return AjaxResult
     */
    @PreAuthorize("@ps.hasPermi('system:facilities:save')")
    @PostMapping("/save")
    public AjaxResult saveFacilities(@RequestBody SysFacilities sysFacilities) {
        sysFacilitiesService.save(sysFacilities);
        return AjaxResult.success();
    }

    /**
     * 删除设施信息
     * @param jsonObject
     * @return AjaxResult
     */
    @PreAuthorize("@ps.hasPermi('system:facilities:delete')")
    @PostMapping("/delete")
    public AjaxResult deleteFacilities(@RequestBody JSONObject jsonObject) {
        if(sysFacilitiesService.delete(jsonObject.getString("id"))){
            return AjaxResult.success();
        }
        return AjaxResult.error();
    }
}
