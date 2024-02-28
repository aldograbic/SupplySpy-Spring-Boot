package com.project.SupplySpy.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReportsController {
    
    @GetMapping("/reports")
    public String getReportsPage() {
        return "reports";
    }
}
