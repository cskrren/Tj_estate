package com.rkr.controller;

import com.rkr.service.SysChatInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/system/chatInfo")
public class SysChatInfoController {

    @Autowired
    private SysChatInfoService sysChatInfoService;//这里会报错，但是并不影响

    private final Integer pageSize = 20;

    @GetMapping("/history")
    public void getChatHistory(Integer groupId, Integer currentSize) {
        sysChatInfoService.getChatHistory(groupId, currentSize, pageSize);
    }
}
