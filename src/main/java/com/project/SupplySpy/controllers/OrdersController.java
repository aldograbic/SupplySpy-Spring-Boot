package com.project.SupplySpy.controllers;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.SupplySpy.classes.Customer;
import com.project.SupplySpy.classes.Inventory;
import com.project.SupplySpy.classes.OrderItem;
import com.project.SupplySpy.classes.SalesOrder;
import com.project.SupplySpy.repositories.customers.CustomerRepository;
import com.project.SupplySpy.repositories.inventory.InventoryRepository;
import com.project.SupplySpy.repositories.order_items.OrderItemRepository;
import com.project.SupplySpy.repositories.sales_orders.SalesOrderRepository;

@Controller
public class OrdersController {

    @Autowired
    private SalesOrderRepository salesOrderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private CustomerRepository customerRepository;
    
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

        List<Inventory> inventory = inventoryRepository.getInventory(totalPages, size);
        model.addAttribute("inventory", inventory);

        return "orders";
    }

    @GetMapping("/orders/{orderId}")
    public String getOrderDetailsPage(@PathVariable("orderId") int orderId,
                                    Model model) {

        SalesOrder salesOrder = salesOrderRepository.findSalesOrderByOrderId(orderId);
        model.addAttribute("salesOrder", salesOrder);

        List<OrderItem> orderItems = orderItemRepository.getOrderItemsForOrderByOrderId(orderId);
        model.addAttribute("orderItems", orderItems);

        BigDecimal priceTotal = BigDecimal.ZERO;
        for (OrderItem orderItem : orderItems) {
            priceTotal = priceTotal.add(orderItem.getPrice().multiply(new BigDecimal(orderItem.getQuantity())));
        }
        model.addAttribute("priceTotal", priceTotal);
        
        BigDecimal priceTax = priceTotal.multiply(new BigDecimal(0.13));
        model.addAttribute("priceTax", priceTax);

        BigDecimal priceAmountPaid = priceTotal.add(priceTax);
        model.addAttribute("priceAmountPaid", priceAmountPaid);

        return "order-details";
    }

    @PostMapping("/insertOrder")
    @Transactional
    public String insertOrder(@ModelAttribute Customer customer, @RequestParam List<OrderItem> orderItems, RedirectAttributes redirectAttributes) {

        try {
            customerRepository.insertCustomer(customer);

            SalesOrder salesOrder = new SalesOrder(customer.getCustomerId());
            salesOrderRepository.insertSalesOrder(salesOrder);

            for (OrderItem orderItem : orderItems) {
                orderItemRepository.insertOrderItem(orderItem);
            }
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "There was a problem creating the order. Please try again.");
        }

        return "redirect:/orders";
    }
}