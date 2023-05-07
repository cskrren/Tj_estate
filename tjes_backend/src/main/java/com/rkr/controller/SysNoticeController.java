package com.rkr.controller;

import com.alibaba.fastjson.JSONObject;
import com.rkr.domain.AjaxResult;
import com.rkr.domain.entity.SysNotice;
import com.rkr.service.SysNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @Package com.rkr.controller
 * @auhter rkr
 * @date 2023/4/30 15:24
 * @description SysNoticeController:公告管理
 */

@RestController
@RequestMapping("/system/notice")
public class SysNoticeController {
    @Autowired
    private SysNoticeService sysNoticeService;// 这里会报错，但是并不影响

    /**
     * 获取所有公告信息
     * @return AjaxResult
     */
    @GetMapping("/list")
    public AjaxResult getList(){
        return AjaxResult.success(sysNoticeService.list());
    }

    /**
     * 获取所有公告名称
     * @param sysNotice
     * @return AjaxResult
     */
    @PreAuthorize("@ps.hasPermi('system:notice:save')")
    @PostMapping("/save")
    public AjaxResult saveFacilities(@RequestBody SysNotice sysNotice) {
        sysNoticeService.save(sysNotice);
        return AjaxResult.success();
    }

    /**
     * 删除公告信息
     * @param jsonObject
     * @return AjaxResult
     */
    @PreAuthorize("@ps.hasPermi('system:notice:delete')")
    @PostMapping("/delete")
    public AjaxResult deleteFacilities(@RequestBody JSONObject jsonObject) {
        if(sysNoticeService.delete(jsonObject.getString("id"))){
            return AjaxResult.success();
        }
        return AjaxResult.error();
    }
}
