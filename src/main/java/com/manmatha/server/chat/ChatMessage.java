package com.manmatha.server.chat;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage implements Serializable{
    private String chatSessionId;
    private String senderId;
    private String message; 
    private String image;
    private String replyTo;
}
