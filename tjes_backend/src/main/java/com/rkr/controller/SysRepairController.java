package com.rkr.controller;

import com.alibaba.fastjson.JSONObject;
import com.rkr.domain.AjaxResult;
import com.rkr.domain.entity.SysRepair;
import com.rkr.service.SysRepairService;
import com.rkr.utils.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @Package com.rkr.config
 * @auhter rkr
 * @date 2023/4/30 15:18
 * @description SysRepairController:报修信息管理
 */

@RestController
@RequestMapping("/system/repair")
public class SysRepairController {
    @Autowired
    private SysRepairService sysRepairService;//这里会报错，但是并不影响

    /**
     * 获取所有报修信息
     * @return
     */
    @GetMapping("/list")
    public AjaxResult getList() {
        return AjaxResult.success(sysRepairService.list());
    }

    /**
     * 获取所有报修信息
     * @return 通过用户id获取报修信息
     */
    @GetMapping("/list/user")
    public AjaxResult getListByUserId() {
        String userId = RequestUtils.getCurrentLoginUser().getUser().getId();
        return AjaxResult.success(sysRepairService.findByUserId(userId));
    }

    /**
     * 保存报修信息
     * @param sysRepair
     * @return 保存报修信息
     */
    @PreAuthorize("@ps.hasPermi('system:repair:save')")
    @PostMapping("/add")
    public AjaxResult addFacilities(@RequestBody SysRepair sysRepair) {
        sysRepairService.add(sysRepair);
        return AjaxResult.success();
    }

    /**
     * 审核报修信息
     * @param sysRepair
     * @return 审核报修信息
     */
    @PreAuthorize("@ps.hasPermi('system:repair:examine')")
    @PostMapping("/examine")
    public AjaxResult examineFacilities(@RequestBody SysRepair sysRepair) {
        sysRepairService.examine(sysRepair);
        return AjaxResult.success();
    }

    /**
     * 删除报修信息
     * @param jsonObject
     * @return 删除报修信息
     */
    @PreAuthorize("@ps.hasPermi('system:repair:delete')")
    @PostMapping("/delete")
    public AjaxResult deleteFacilities(@RequestBody JSONObject jsonObject) {
        if (sysRepairService.delete(jsonObject.getString("id"))) {
            return AjaxResult.success();
        }
        return AjaxResult.error();
    }
}
