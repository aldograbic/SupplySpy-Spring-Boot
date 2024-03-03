package com.project.SupplySpy.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.SupplySpy.classes.Notification;
import com.project.SupplySpy.repositories.notifications.NotificationRepository;
import com.project.SupplySpy.repositories.users.UserRepository;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class NotificationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @PostMapping("/approveUser")
    @PreAuthorize("hasRole('MANAGER')")
    public String approveUser(@ModelAttribute Notification notification, HttpServletRequest request, RedirectAttributes redirectAttributes) {

        try {
            userRepository.approveUserByUserId(notification.getSenderId());
            notificationRepository.markAsReadApproval(notification);

            redirectAttributes.addFlashAttribute("successMessage", "User has been approved successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error approving user.");
        }
        
        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/");
    }

    @PostMapping("/declineUser")
    @PreAuthorize("hasRole('MANAGER')")
    public String declineUser(@ModelAttribute Notification notification, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        
        try {
            notificationRepository.markAsReadApproval(notification);

            redirectAttributes.addFlashAttribute("successMessage", "User has been declined successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error declining user.");
        }

        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/");
    }
}