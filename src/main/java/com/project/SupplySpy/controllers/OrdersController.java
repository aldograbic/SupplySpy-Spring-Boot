package com.project.SupplySpy.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.SupplySpy.classes.SalesOrder;
import com.project.SupplySpy.repositories.sales_orders.SalesOrderRepository;

@Controller
public class OrdersController {

    @Autowired
    private SalesOrderRepository salesOrderRepository;
    
    @GetMapping("/orders")
    public String getOrdersPage(Model model,
                                @RequestParam(name = "page", defaultValue = "1") int page,
                                @RequestParam(name = "size", defaultValue = "10") int size) {

        List<SalesOrder> salesOrders = salesOrderRepository.getSalesOrders(page, size);
        model.addAttribute("salesOrders", salesOrders);

        int totalItems = salesOrderRepository.getTotalSalesOrdersCount();
        int totalPages = (int) Math.ceil((double) totalItems / size);
        totalPages = Math.max(totalPages, 1);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);
        return "orders";
    }
}
