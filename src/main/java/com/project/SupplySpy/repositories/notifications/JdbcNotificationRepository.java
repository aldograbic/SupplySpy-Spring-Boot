package com.project.SupplySpy.repositories.notifications;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.project.SupplySpy.classes.Notification;
import com.project.SupplySpy.classes.User;
import com.project.SupplySpy.repositories.users.UserRepository;

@Repository
public class JdbcNotificationRepository implements NotificationRepository{
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void insertNotification(Notification notification) {
        String sql = "INSERT INTO notifications (sender_id, receiver_id, message, status, type) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, notification.getSenderId(), notification.getReceiverId(), notification.getMessage(), notification.getStatus(), notification.getType());
    }

    @Override
    public List<Notification> getNotificationsForUser(User user) {
        String sql = "SELECT notification_id, sender_id, receiver_id, message, status, type FROM notifications WHERE receiver_id = ? AND status = 'UNREAD'";
        return jdbcTemplate.query(sql, new NotificationRowMapper(userRepository), user.getUserId());
    }

    @Override
    public void markAsReadApproval(Notification notification) {
        String sql = "UPDATE notifications SET status = 'READ' WHERE type = 'REGISTRATION_APPROVAL' AND sender_id = ?";
        jdbcTemplate.update(sql, notification.getSenderId());
    }
}