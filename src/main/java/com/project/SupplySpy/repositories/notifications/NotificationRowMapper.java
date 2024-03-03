package com.project.SupplySpy.repositories.notifications;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import com.project.SupplySpy.classes.Notification;
import com.project.SupplySpy.classes.User;
import com.project.SupplySpy.repositories.users.UserRepository;

public class NotificationRowMapper implements RowMapper<Notification>{

    private UserRepository userRepository;

    public NotificationRowMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Nullable
    public Notification mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
        
        Notification notification = new Notification();
        notification.setNotificationId(rs.getInt("notification_id"));
        notification.setMessage(rs.getString("message"));
        notification.setStatus(rs.getString("status"));
        notification.setType(rs.getString("type"));
        notification.setSenderId(rs.getInt("sender_id"));
        notification.setReceiverId(rs.getInt("receiver_id"));

        int senderId = rs.getInt("sender_id");
        User sender = userRepository.findByUserId(senderId);
        notification.setSender(sender);

        return notification;
    }
}