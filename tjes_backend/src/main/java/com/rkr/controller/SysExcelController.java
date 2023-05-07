package com.rkr.controller;

import com.rkr.service.SysExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @Package com.rkr.controller
 * @auhter rkr
 * @date 2023/4/30 15:24
 * @description SysExcelController:excel下载
 */

@RestController
@RequestMapping("/system/download/excel")
public class SysExcelController {
    @Autowired
    private SysExcelService sysExcelService;//这里会报错，但是并不影响

    /**
     * 下载所有房间信息
     * @param httpServletResponse
     */
    //@PreAuthorize("@ps.hasPermi('system:rooms:download')")
    @PreAuthorize("true")
    @GetMapping("/rooms")
    public void downloadSysRooms(HttpServletResponse httpServletResponse){
        sysExcelService.downloadSysRooms(httpServletResponse);
    }

    /**
     * 下载所有楼栋信息
     * @param httpServletResponse
     */
    //@PreAuthorize("@ps.hasPermi('system:user_HouseholdInfo:download')")
    @PreAuthorize("true")
    @GetMapping("/households")
    public void downloadSysHouseholdInfos(HttpServletResponse httpServletResponse){
        sysExcelService.downloadSysHouseholdInfos(httpServletResponse);
    }
}
