package com.project.SupplySpy.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.SupplySpy.classes.Customer;
import com.project.SupplySpy.repositories.customers.CustomerRepository;

@Controller
public class CustomersController {

    @Autowired
    private CustomerRepository customerRepository;
    
    @GetMapping("/customers")
    public String getCustomersPage(Model model,
                                @RequestParam(name = "page", defaultValue = "1") int page,
                                @RequestParam(name = "size", defaultValue = "10") int size) {

        List<Customer> customers = customerRepository.getCustomers(page, size);
        model.addAttribute("customers", customers);

        int totalItems = customerRepository.getTotalCustomersCount();
        int totalPages = (int) Math.ceil((double) totalItems / size);
        totalPages = Math.max(totalPages, 1);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);
        return "customers";
    }
}