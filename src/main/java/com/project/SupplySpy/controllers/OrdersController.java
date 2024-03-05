package com.project.SupplySpy.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.SupplySpy.classes.OrderItem;
import com.project.SupplySpy.classes.SalesOrder;
import com.project.SupplySpy.repositories.order_items.OrderItemRepository;
import com.project.SupplySpy.repositories.sales_orders.SalesOrderRepository;

@Controller
public class OrdersController {

    @Autowired
    private SalesOrderRepository salesOrderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;
    
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

    @GetMapping("/orders/{orderId}")
    public String getOrderDetailsPage(@PathVariable("orderId") int orderId,
                                    Model model) {

        SalesOrder salesOrder = salesOrderRepository.findSalesOrderByOrderId(orderId);
        model.addAttribute("salesOrder", salesOrder);

        List<OrderItem> orderItems = orderItemRepository.getOrderItemsForOrderByOrderId(orderId);
        model.addAttribute("orderItems", orderItems);

        return "order-details";
    }
}