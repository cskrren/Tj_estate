package com.rkr.controller;

import com.alibaba.fastjson.JSONObject;
import com.rkr.domain.AjaxResult;
import com.rkr.domain.entity.SysRoom;
import com.rkr.service.SysRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @Package com.rkr.controller
 * @auhter rkr
 * @date 2023/4/30 15:24
 * @description SysRoomController:房间信息管理
 */

@RestController
@RequestMapping("/system/room")
public class SysRoomController {
    @Autowired
    private SysRoomService sysRoomService;//这里会报错，但是并不影响

    /**
     * 获取所有房间信息
     * @return AjaxResult
     */
    @GetMapping("/list")
    public AjaxResult getList(){
        return AjaxResult.success(sysRoomService.list());
    }

    /**
     * 获取对应楼栋的所有房间信息
     * @return AjaxResult
     */
    @GetMapping("unitName/list/{buildingName}")
    public AjaxResult getUnitNameList(@PathVariable("buildingName") String buildingName){
        return AjaxResult.success(sysRoomService.unitNameList(buildingName));
    }

    /**
     * 保存房间
     * @return AjaxResult
     */
    @PreAuthorize("@ps.hasPermi('system:room:save')")
    @PostMapping("/save")
    public AjaxResult saveFacilities(@RequestBody SysRoom sysRoom) {
        sysRoomService.save(sysRoom);
        return AjaxResult.success();
    }

    /**
     * 删除房间信息
     * @param jsonObject
     * @return AjaxResult
     */
    @PreAuthorize("@ps.hasPermi('system:room:delete')")
    @PostMapping("/delete")
    public AjaxResult deleteFacilities(@RequestBody JSONObject jsonObject) {
        if (sysRoomService.delete(jsonObject.getString("id"))) {
            return AjaxResult.success();
        }
        return AjaxResult.error();
    }
}
