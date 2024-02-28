package com.project.SupplySpy.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SuppliersController {
    
    @GetMapping("/suppliers")
    public String getSuppliersPage() {
        return "suppliers";
    }
}
