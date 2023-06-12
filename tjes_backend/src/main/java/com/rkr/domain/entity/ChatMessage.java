package com.rkr.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {
    private Integer groupId;
    private String sender;
    private String image;
    private String content;
    private String currentTime;
    private MessageType type;
    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE
    }
}
