package com.project.SupplySpy.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.project.SupplySpy.classes.Notification;
import com.project.SupplySpy.classes.User;
import com.project.SupplySpy.repositories.notifications.NotificationRepository;
import com.project.SupplySpy.repositories.users.UserRepository;

@ControllerAdvice
public class NotificationAdvice {
    
    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;
    
    @ModelAttribute("notifications")
    public List<Notification> notifications() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);
        if (user != null) {
            return notificationRepository.getNotificationsForUser(user);
        } else {
            return null;
        }
    }
}