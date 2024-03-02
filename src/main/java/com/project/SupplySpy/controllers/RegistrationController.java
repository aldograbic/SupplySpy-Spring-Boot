package com.project.SupplySpy.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.SupplySpy.classes.User;
import com.project.SupplySpy.repositories.users.UserRepository;

@Controller
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @GetMapping("/registration")
    public String getRegistrationPage() {
        return "registration";
    }

    @PostMapping("/registration")
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

        userRepository.insertUser(user);

        redirectAttributes.addFlashAttribute("successMessage", "Registration successful! You can now log in with your SupplySpy account.");

        return "redirect:/";
    }
}