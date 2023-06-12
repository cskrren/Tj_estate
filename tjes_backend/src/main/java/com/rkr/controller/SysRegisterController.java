package com.rkr.controller;

import com.alibaba.fastjson.JSONObject;
import com.rkr.domain.AjaxResult;
import com.rkr.domain.entity.SysRegister;
import com.rkr.domain.entity.SysUser;
import com.rkr.service.SysRegisterService;
import com.rkr.service.SysUserInfoService;
import com.rkr.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Package com.rkr.controller
 * @auhter rkr
 * @date 2023/4/30 15:24
 * @description SysRegisterController:注册控制器
 */

@RestController
@RequestMapping()
public class SysRegisterController {
    @Autowired
    private SysRegisterService sysRegisterService;

    @Autowired
    private SysUserInfoService sysUserInfoService;

    /**
     * 注册
     * @param sysRegister
     * @return AjaxResult
     */

    @PostMapping("/register")
    public AjaxResult register(@RequestBody SysRegister sysRegister) {
         SysUser user = sysRegisterService.register(sysRegister);
        if (user==null) {
            return AjaxResult.error("用户名已存在");
        }
        sysUserInfoService.insertUserInfo(user);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userName",user.getUserName());
        System.out.println(jsonObject);
        return AjaxResult.success(jsonObject);
    }
}
