package com.project.SupplySpy.classes;

public class Notification {
    private int notificationId;
    private String message;
    private String status;
    private String type;
    private int senderId;
    private int receiverId;

    private User sender;

    public Notification() {}

    public Notification(String message, String status, String type, int senderId, int receiverId) {
        this.message = message;
        this.status = status;
        this.type = type;
        this.senderId = senderId;
        this.receiverId = receiverId;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }
}