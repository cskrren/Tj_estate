package com.rkr.controller;

import com.rkr.domain.AjaxResult;
import com.rkr.domain.entity.SysOptions;
import com.rkr.service.SysOptionsService;
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
@RequestMapping("/system/options")
public class SysOptionsController {
    @Autowired
    private SysOptionsService sysOptionsService;

    /**
     * 获取所有楼栋信息
     * @return AjaxResult
     */
    @GetMapping("/get/{id}")
    public AjaxResult getOptions(@PathVariable("id") String id) {
        return AjaxResult.success(sysOptionsService.findById(id));
    }

    /**
     * 获取所有楼栋名称
     * @return AjaxResult
     */
    @PreAuthorize("@ps.hasPermi('system:options:save')")
    @PostMapping("/save")
    public AjaxResult saveOptions(@RequestBody SysOptions sysOptions) {
        sysOptionsService.save(sysOptions);
        return AjaxResult.success();
    }
}
