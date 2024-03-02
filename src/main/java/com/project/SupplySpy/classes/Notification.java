package com.project.SupplySpy.classes;

public class Notification {
    private int notificationId;
    private String message;
    private String status;
    private int senderId;
    private int receiverId;

    public Notification() {}

    public Notification(String message, String status, int senderId, int receiverId) {
        this.message = message;
        this.status = status;
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
}
