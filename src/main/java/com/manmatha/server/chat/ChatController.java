package com.manmatha.server.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @Autowired
    private MatchService matchService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    // When client sends /app/connect with their userId
    @MessageMapping("/connect")
    public void connectUser(String userId) {
        String partnerId = matchService.connectUser(userId);
        if (partnerId != null) {
            // Notify both users they are connected
            messagingTemplate.convertAndSend("/topic/" + userId, "Matched with " + partnerId);
            messagingTemplate.convertAndSend("/topic/" + partnerId, "Matched with " +
                    userId);
        } else {
            messagingTemplate.convertAndSend("/topic/" + userId, "Waiting for a partner...");
        }
    }



    @MessageMapping("/chat")
    public void processMessage(ChatMessage chatMessage) {

        String partnerId = matchService.getPartner(chatMessage.getSenderId());

        if (partnerId != null) {
            // send message to partner’s WebSocket topic
            messagingTemplate.convertAndSend("/topic/" + partnerId, chatMessage);
        }
    }

}
