package com.rkr.controller;

import com.alibaba.fastjson.JSONObject;
import com.rkr.domain.AjaxResult;
import com.rkr.domain.entity.SysPasswordFind;
import com.rkr.domain.entity.SysRegister;
import com.rkr.domain.entity.SysUser;
import com.rkr.service.SysPasswordFindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Package com.rkr.controller
 * @auhter rkr
 * @date 2023/4/30 15:24
 * @description SysPasswordFindController:找回密码
 */

@RestController
@RequestMapping()
public class SysPasswordFindController {
    @Autowired
    private SysPasswordFindService sysPasswordFindService;

    /**
     * 注册
     * @param sysPasswordFind
     * @return AjaxResult
     */

    @PostMapping("/passwordfind")
    public AjaxResult passwordfind(@RequestBody SysPasswordFind sysPasswordFind) {
        SysUser user = sysPasswordFindService.passwordFind(sysPasswordFind);
        if (user==null) {
            return AjaxResult.error("用户名不存在");
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userName",user.getUserName());
        System.out.println(jsonObject);
        return AjaxResult.success(jsonObject);
    }
}
