package com.crisis.crisisproject.service;

import com.crisis.crisisproject.model.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public NotificationService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendGlobalNotification(final String content, final String location, final String alertType) {
        ResponseMessage message = new ResponseMessage("Global Notification: "+content, location, alertType);

        messagingTemplate.convertAndSend("/topic/global-notifications", message);
    }

    public void sendPrivateNotification(final String userId, final String content, final String location, final String alertType) {
        ResponseMessage message = new ResponseMessage("Private Notification : "+content, location, alertType);

        messagingTemplate.convertAndSendToUser(userId,"/topic/private-notifications", message);
    }
}
