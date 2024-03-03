package com.project.SupplySpy.repositories.notifications;

import java.util.List;

import com.project.SupplySpy.classes.Notification;
import com.project.SupplySpy.classes.User;

public interface NotificationRepository {
    void insertNotification(Notification notification);
    List<Notification> getNotificationsForUser(User user);
    void markAsReadApproval(Notification notification);
}