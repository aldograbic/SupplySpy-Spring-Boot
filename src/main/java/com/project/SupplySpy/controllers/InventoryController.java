package com.project.SupplySpy.controllers;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.project.SupplySpy.classes.Inventory;
import com.project.SupplySpy.classes.Product;
import com.project.SupplySpy.repositories.inventory.InventoryRepository;
import com.project.SupplySpy.repositories.products.ProductRepository;

@Controller
public class InventoryController {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ProductRepository productRepository;
    
    @GetMapping("/inventory")
    public String getInventoryPage(Model model,
                                @RequestParam(name = "page", defaultValue = "1") int page,
                                @RequestParam(name = "size", defaultValue = "10") int size) {

        List<Inventory> inventory = inventoryRepository.getInventory(page, size);
        model.addAttribute("inventory", inventory);

        int totalItems = inventoryRepository.getTotalInventoryCount();
        int totalPages = (int) Math.ceil((double) totalItems / size);
        totalPages = Math.max(totalPages, 1);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);

        return "inventory";
    }

    @PostMapping("/insertInventory")
    @Transactional
    public String insertInventory(@RequestParam("name") String name, 
                                  @RequestParam("description") String description, 
                                  @RequestParam("image") MultipartFile image,
                                  @RequestParam("price") BigDecimal price, 
                                  @RequestParam("location") String location, 
                                  @RequestParam("quantity") int quantity) {

        Product product = new Product(name, description, null, price);
        productRepository.insertProduct(product);
        
        Inventory inventory = new Inventory(product.getProductId(), quantity, location);
        inventoryRepository.insertInventory(inventory);

        return "redirect:/inventory";
    }
}