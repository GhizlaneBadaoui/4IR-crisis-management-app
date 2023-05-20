package com.crisis.crisisproject.model;

public class ResponseMessage {
    private String content;
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

    public ResponseMessage() {
    }

    public ResponseMessage(String content, String location, String alertType) {
        this.content = content;
        this.location = location;
        this.alertType = alertType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "ResponseMessage{" +
                "content='" + content + '\'' +
                ", location='" + location + '\'' +
                ", alertType='" + alertType + '\'' +
                '}';
    }
}
