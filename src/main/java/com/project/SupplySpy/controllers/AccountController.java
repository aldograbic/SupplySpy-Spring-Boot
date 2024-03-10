package com.project.SupplySpy.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.SupplySpy.classes.User;
import com.project.SupplySpy.repositories.users.UserRepository;

@Controller
public class AccountController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User getAuthUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);
        return user;
    }
    
    @GetMapping("/account")
    public String getAccountPage(Model model) {
        User user = getAuthUser();
        model.addAttribute("user", user);

        return "account";
    }

    @PostMapping("/updateUser")
    public String updateUser(RedirectAttributes redirectAttributes) {
        User user = getAuthUser();

        try {
            userRepository.updateUser(user);
            redirectAttributes.addFlashAttribute("successMessage", "Account successfully updated.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "There was a problem updating personal information. Please try again.");
        }

        return "redirect:/account";
    }

    @PostMapping("/updatePassword")
    public String updatePassword(@RequestParam("currentPassword") String currentPassword, @RequestParam("newPassword") String newPassword ,RedirectAttributes redirectAttributes) {
        User user = getAuthUser();

        String encryptedCurrentPassword = passwordEncoder.encode(currentPassword);

        if (encryptedCurrentPassword.equals(user.getPassword())) {
            try {
                String encrypterNewPassword = passwordEncoder.encode(newPassword);
                user.setPassword(encrypterNewPassword);
                userRepository.updatePassword(user);
                redirectAttributes.addFlashAttribute("successMessage", "Account successfully updated.");
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("errorMessage", "There was a problem updating the password. Please try again.");
            }
        }

        return "redirect:/account";
    }

    @PostMapping("/deleteUser")
    public String deleteUser(RedirectAttributes redirectAttributes) {
        User user = getAuthUser();
        try {
            userRepository.deleteUser(user);
            redirectAttributes.addFlashAttribute("successMessage", "Account successfully deleted.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "There was a problem with deleting your account. Please try again.");
            return "redirect:/account";
        }

        return "redirect:/login";
    }
}