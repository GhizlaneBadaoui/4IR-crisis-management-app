package com.crisis.crisisproject.service;

import com.crisis.crisisproject.model.ResponseMessage;
import com.crisis.crisisproject.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WSService {

    private final SimpMessagingTemplate messagingTemplate;
    private final NotificationService notificationService;

    @Autowired
    public WSService(SimpMessagingTemplate messagingTemplate, NotificationService notificationService) {
        this.messagingTemplate = messagingTemplate;
        this.notificationService = notificationService;
    }

    public void notifyFrontend(final String message, final String location, final String alertType) {
        ResponseMessage response = new ResponseMessage(message, location, alertType);
        notificationService.sendGlobalNotification(message, location, alertType);

        messagingTemplate.convertAndSend("/topic/messages", response);
    }

    public void notifyUser(final String id, final String message, final String location, final String alertType) {
        ResponseMessage response = new ResponseMessage(message, location, alertType);

        notificationService.sendPrivateNotification(id, message, location, alertType);
        messagingTemplate.convertAndSendToUser(id, "/topic/private-messages", response);
    }
}
