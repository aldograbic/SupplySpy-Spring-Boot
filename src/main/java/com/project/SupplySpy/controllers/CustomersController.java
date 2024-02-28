package com.project.SupplySpy.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomersController {
    
    @GetMapping("/customers")
    public String getCustomersPage() {
        return "customers";
    }
}
