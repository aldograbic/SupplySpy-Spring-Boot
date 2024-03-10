package com.project.SupplySpy.controllers;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.project.SupplySpy.repositories.inventory.InventoryRepository;
import com.project.SupplySpy.repositories.order_items.OrderItemRepository;
import com.project.SupplySpy.repositories.sales_orders.SalesOrderRepository;

@Controller
public class DashboardController {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private SalesOrderRepository salesOrderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;
    
    @GetMapping("/dashboard")
    public String getDashboardPage(Model model) {

        int countInventory = inventoryRepository.getTotalInventoryCount();
        model.addAttribute("countInventory", countInventory);

        int countNoQuantityInventory = inventoryRepository.getNoQuantityInventoryCount();
        model.addAttribute("countNoQuantityInventory", countNoQuantityInventory);

        int countCompletedSalesOrders = salesOrderRepository.getCompletedSalesOrdersCount();
        model.addAttribute("countCompletedSalesOrders", countCompletedSalesOrders);

        BigDecimal totalRevenue = orderItemRepository.getTotalRevenue();
        model.addAttribute("totalRevenue", totalRevenue);

        return "index";
    }

    @GetMapping("/")
    public String defaultAfterLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            return "redirect:/dashboard";
        } else {
            return "redirect:/login";
        }
    }
}
