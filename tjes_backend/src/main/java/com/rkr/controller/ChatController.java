package com.rkr.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rkr.domain.entity.ChatMessage;
import com.rkr.domain.entity.OnlineUser;
import com.rkr.domain.entity.SysChat;
import com.rkr.domain.entity.SysChatInfo;
import com.rkr.service.RedisService;
import com.rkr.service.SysChatInfoService;
import com.rkr.service.SysChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ChatController {

    @Autowired
    private SysChatInfoService sysChatInfoService;

    @Autowired
    private RedisService redisService;

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        SysChatInfo newMessage = new SysChatInfo();
        newMessage.setGroupId(chatMessage.getGroupId());
        newMessage.setUserName(chatMessage.getSender());
        newMessage.setUserAvatar(chatMessage.getImage());
        newMessage.setText(chatMessage.getContent());
        newMessage.setDate(new Date());
        sysChatInfoService.addChatInfo(newMessage);
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public JSONObject addUser(@Payload ChatMessage chatMessage,
                             SimpMessageHeaderAccessor headerAccessor) {
        String redisKey = "Group";
        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        if (!redisService.hasKey(redisKey)) {
            Map<String, String> map = new HashMap<>();
            redisService.setHash(redisKey, map);
        }
        OnlineUser newUser = new OnlineUser();
        newUser.setUserName(chatMessage.getSender());
        newUser.setUserAvatar(chatMessage.getImage());
        redisService.setOne(redisKey, chatMessage.getSender(), newUser);
        List<OnlineUser> onlineUsers = redisService.getHash(redisKey, OnlineUser.class);
        System.out.println(onlineUsers);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("onlineUsers", onlineUsers);
        jsonObject.put("type", chatMessage.getType());
        jsonObject.put("sender", chatMessage.getSender());
        return jsonObject;
    }
}
