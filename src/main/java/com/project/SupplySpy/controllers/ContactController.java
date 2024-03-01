package com.project.SupplySpy.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContactController {
    
    @GetMapping("/contact")
    public String getContactPage() {
        return "contact";
    }
}
