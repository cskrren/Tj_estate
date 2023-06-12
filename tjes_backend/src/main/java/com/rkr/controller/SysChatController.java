package com.rkr.controller;

import com.alibaba.fastjson.JSON;
import com.rkr.domain.constant.RedisKeyConstants;
import com.rkr.domain.entity.SysChat;
import com.rkr.domain.entity.SysUserInfo;
import com.rkr.service.RedisService;
import com.rkr.service.SysChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/system/chat")
public class SysChatController {

    @Autowired
    private SysChatService sysChatService;//这里会报错，但是并不影响

    @Resource
    private RedisService redisService;

    @GetMapping("/list")
    public void getChatList(){
        sysChatService.getChatList();
    }

    @PostMapping("/add")
    public void addChat(@RequestBody SysChat sysChat){
        sysChatService.addChat(sysChat);
    }

    @PostMapping ("/userList")
    public List<SysUserInfo> getChatUserList(@RequestParam Integer GroupId){
        return sysChatService.getChatUserList(GroupId);
    }
}
