package com.project.SupplySpy.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.SupplySpy.classes.Notification;
import com.project.SupplySpy.classes.User;
import com.project.SupplySpy.repositories.notifications.NotificationRepository;
import com.project.SupplySpy.repositories.users.UserRepository;

@Controller
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @GetMapping("/registration")
    public String getRegistrationPage() {
        return "registration";
    }

    @PostMapping("/registration")
    @Transactional
    public String processRegistration(@ModelAttribute User user, @RequestParam("confirmPassword") String confirmPassword ,RedirectAttributes redirectAttributes) {

        User existingUserUsername = userRepository.findByUsername(user.getUsername());
        if (existingUserUsername != null) {
            redirectAttributes.addFlashAttribute("errorMessage", "User already exists with the same username. Please try again.");
            return "redirect:/registration";
        }

        User existingUserEmail = userRepository.findByEmail(user.getEmail());
        if (existingUserEmail != null) {
            redirectAttributes.addFlashAttribute("errorMessage", "User already exists with the same e-mail address. Please try again.");
            return "redirect:/registration";
        }

        if (!user.getPassword().equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Passwords must match in order to register. Please try again.");
            return "redirect:/registration";
        }
        
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        user.setApproved(false);
        userRepository.insertUser(user);

        List<User> managers = userRepository.findByRole("ROLE_MANAGER");
        Notification notification = new Notification();
        notification.setMessage("A new staff member has registered and requires approval.");

        for (User manager : managers) {
            notification.setReceiverId(manager.getUserId());
            notification.setSenderId(user.getUserId());
            notification.setStatus("UNREAD");
            notificationRepository.insertNotification(notification);
        }

        redirectAttributes.addFlashAttribute("successMessage", "Registration successful! Your account is pending approval. You will be able to log in once your account has been approved by a manager.");

        return "redirect:/login";
    }
}