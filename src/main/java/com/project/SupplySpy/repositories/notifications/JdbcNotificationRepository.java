package com.project.SupplySpy.repositories.notifications;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.project.SupplySpy.classes.Notification;

@Repository
public class JdbcNotificationRepository implements NotificationRepository{
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void insertNotification(Notification notification) {
        String sql = "INSERT INTO notifications (sender_id, receiver_id, message, status) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, notification.getSenderId(), notification.getReceiverId(), notification.getMessage(), notification.getStatus());
    }
}