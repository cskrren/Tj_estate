package com.rkr.listener;

import com.alibaba.fastjson.JSONObject;
import com.rkr.domain.entity.ChatMessage;
import com.rkr.domain.entity.OnlineUser;
import com.rkr.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.List;

@Component
public class WebSocketEventListener {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @Autowired
    private RedisService redisService;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        logger.info("Received a new web socket connection");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if(username != null) {
            logger.info("User Disconnected : " + username);

            String redisKey = "Group";
            redisService.deleteOne(redisKey, username);
            List<OnlineUser> onlineUsers = redisService.getHash(redisKey, OnlineUser.class);
            System.out.println(onlineUsers);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("onlineUsers", onlineUsers);
            jsonObject.put("type", ChatMessage.MessageType.LEAVE);
            jsonObject.put("sender", username);
            messagingTemplate.convertAndSend("/topic/public", jsonObject);
        }
    }
}
