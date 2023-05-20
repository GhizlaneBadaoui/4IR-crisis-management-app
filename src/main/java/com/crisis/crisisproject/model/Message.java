package com.crisis.crisisproject.model;

public class Message {
    private String messageContent;
    private String location;
    private String alertType;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAlertType() {
        return alertType;
    }

    public void setAlertType(String alertType) {
        this.alertType = alertType;
    }

    public String getMessageContent() {
        return messageContent;
    }

    @Override
    public String toString() {
        return "messageContent:'" + messageContent + '\'' +
                ", location:'" + location + '\'' +
                ", alertType='" + alertType + '\'';
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }
}
