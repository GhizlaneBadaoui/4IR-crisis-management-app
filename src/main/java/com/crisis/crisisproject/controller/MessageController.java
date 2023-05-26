package com.crisis.crisisproject.controller;

import com.crisis.crisisproject.model.DecisionTree;
import com.crisis.crisisproject.service.NotificationService;
import com.crisis.crisisproject.model.Message;
import com.crisis.crisisproject.model.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import java.security.Principal;

@Controller
public class MessageController {
    @Autowired
    private NotificationService notificationService;

    @MessageMapping("/message")
    @SendTo("/topic/messages")
    public ResponseMessage getMessage(final Message message) throws Exception {
        System.out.println("message reçu:"+message.getMessageContent() + "--" + message.getLocation() + "--" + message.getAlertType());
        Thread.sleep(1000);
        //notificationService.sendGlobalNotification();
        //System.out.println("message reçu:"+message);

//        return new ResponseMessage(HtmlUtils.htmlEscape(message.getMessageContent()),
//         HtmlUtils.htmlEscape(message.getLocation()),
//         HtmlUtils.htmlEscape(message.getAlertType()));

        DecisionTree decisonTree=new DecisionTree();
        ResponseMessage responseMessage=new ResponseMessage(HtmlUtils.htmlEscape(message.getMessageContent()),
                HtmlUtils.htmlEscape(message.getLocation()),
                HtmlUtils.htmlEscape(message.getAlertType()));
        responseMessage.setLocation(decisonTree.StringToWordVector(HtmlUtils.htmlEscape(message.getMessageContent())));
        return responseMessage;
    }

    @MessageMapping("/private-message")
    @SendToUser("/topic/private-messages")
    public ResponseMessage getPrivateMessage(final Message message, final Principal principal) throws InterruptedException {
        Thread.sleep(1000);
        notificationService.sendPrivateNotification(principal.getName(), message.getMessageContent(), message.getLocation(), message.getAlertType());
        return new ResponseMessage(HtmlUtils.htmlEscape(
                "Sending private message to user " + principal.getName() + ": "
                        + message.getMessageContent()), HtmlUtils.htmlEscape(message.getLocation()),
                HtmlUtils.htmlEscape(message.getAlertType())
        );
    }
}
