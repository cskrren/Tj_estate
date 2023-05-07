package com.rkr.controller;

import com.rkr.domain.AjaxResult;
import com.rkr.domain.entity.SysSmsCode;
import com.rkr.service.SysSmsCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Package com.rkr.controller
 * @auhter rkr
 * @date 2023/4/30 15:24
 * @description SysRoomController:房间信息管理
 */

@RestController
@RequestMapping("/system/smsCode")
public class SysSmsCodeController {

    @Autowired
    private SysSmsCodeService smsCodeService;


    /**
     * 发送短信
     * @param sysSmsCode
     * @return AjaxResult
     */
    @PostMapping("/register")
    public AjaxResult sendSmsCode(@RequestBody SysSmsCode sysSmsCode) {
        System.out.println(sysSmsCode);
        boolean p = smsCodeService.sendSmsCode(sysSmsCode);
        if(p){
            return AjaxResult.success("发送成功");
        }
        else{
            return AjaxResult.error("发送失败");
        }
    }
}
