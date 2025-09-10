package com.manmatha.server.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class MatchService {

    private final Queue<String> waitingUsers = new ConcurrentLinkedQueue<>();
    private final ConcurrentHashMap<String, String> activePairs = new ConcurrentHashMap<>();

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public synchronized String connectUser(String userId) {

        String isExists = getPartner(userId);
        if (isExists != null) {
            messagingTemplate.convertAndSend("/topic/" + isExists, "Chat disconnect with " +
                    userId);
            disconnectUser(userId);
        }

        if (waitingUsers.isEmpty()) {
            waitingUsers.add(userId);
            return null; // no partner yet
        } else {
            String partnerId = waitingUsers.poll();
            activePairs.put(userId, partnerId);
            activePairs.put(partnerId, userId);
            return partnerId; // matched!
        }
    }

    public String getPartner(String userId) {
        return activePairs.get(userId);
    }

    public synchronized void disconnectUser(String userId) {
        waitingUsers.remove(userId);
        String partner = activePairs.remove(userId);
        if (partner != null) {
            activePairs.remove(partner);
        }
    }
}
