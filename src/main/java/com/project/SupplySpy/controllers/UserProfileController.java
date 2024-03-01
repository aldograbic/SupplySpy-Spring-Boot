package com.project.SupplySpy.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserProfileController {
    
    @GetMapping("/user-profile")
    public String getUserProfilePage() {
        
        return "user-profile";
    }
}