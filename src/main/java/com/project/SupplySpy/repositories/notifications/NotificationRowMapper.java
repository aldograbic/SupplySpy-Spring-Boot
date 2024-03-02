package com.project.SupplySpy.repositories.notifications;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import com.project.SupplySpy.classes.Notification;

public class NotificationRowMapper implements RowMapper<Notification>{

    @Override
    @Nullable
    public Notification mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
        
        Notification notification = new Notification();
        notification.setNotificationId(rs.getInt("notification_id"));
        notification.setMessage(rs.getString("message"));
        notification.setStatus(rs.getString("status"));
        notification.setSenderId(rs.getInt("sender_id"));
        notification.setReceiverId(rs.getInt("receiver_id"));

        return notification;
    }
}