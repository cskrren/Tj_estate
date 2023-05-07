package com.rkr.controller;

import com.alibaba.fastjson.JSONObject;
import com.rkr.domain.AjaxResult;
import com.rkr.domain.entity.SysComplaint;
import com.rkr.service.SysComplaintService;
import com.rkr.utils.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @Package com.rkr.controller
 * @auhter rkr
 * @date 2023/4/30 15:24
 * @description SysComplaintController:投诉信息管理
 */

@RestController
@RequestMapping("/system/complaint")
public class SysComplaintController {
    @Autowired
    private SysComplaintService sysComplaintService;//这里会报错，但是并不影响

    /**
     * 获取所有投诉信息
     * @return AjaxResult
     */
    @GetMapping("/list")
    public AjaxResult getList(){
        return AjaxResult.success(sysComplaintService.list());
    }

    /**
     * 获取所有投诉信息
     * @return AjaxResult
     */
    @GetMapping("/list/user")
    public AjaxResult getListByUserId(){
        String userId = RequestUtils.getCurrentLoginUser().getUser().getId();
        return AjaxResult.success(sysComplaintService.findByUserId(userId));
    }

    /**
     * 保存投诉信息
     * @param sysComplaint
     * @return AjaxResult
     */
    @PreAuthorize("@ps.hasPermi('system:complaint:save')")
    @PostMapping("/add")
    public AjaxResult addFacilities(@RequestBody SysComplaint sysComplaint) {
        sysComplaintService.add(sysComplaint);
        return AjaxResult.success();
    }

    /**
     * 审核投诉信息
     * @param sysComplaint
     * @return AjaxResult
     */
    @PreAuthorize("@ps.hasPermi('system:complaint:examine')")
    @PostMapping("/examine")
    public AjaxResult examineFacilities(@RequestBody SysComplaint sysComplaint) {
        sysComplaintService.examine(sysComplaint);
        return AjaxResult.success();
    }

    /**
     * 删除投诉信息
     * @param jsonObject
     * @return AjaxResult
     */
    @PreAuthorize("@ps.hasPermi('system:complaint:delete')")
    @PostMapping("/delete")
    public AjaxResult deleteFacilities(@RequestBody JSONObject jsonObject) {
        if(sysComplaintService.delete(jsonObject.getString("id"))){
            return AjaxResult.success();
        }
        return AjaxResult.error();
    }
}
