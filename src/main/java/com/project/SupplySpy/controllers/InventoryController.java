package com.project.SupplySpy.controllers;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.project.SupplySpy.classes.Inventory;
import com.project.SupplySpy.classes.Product;
import com.project.SupplySpy.classes.User;
import com.project.SupplySpy.repositories.inventory.InventoryRepository;
import com.project.SupplySpy.repositories.products.ProductRepository;
import com.project.SupplySpy.repositories.users.UserRepository;

@Controller
public class InventoryController {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;
    
    @GetMapping("/inventory")
    public String getInventoryPage(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);

        List<Inventory> inventory = inventoryRepository.getInventoryForUserByUserId(user.getUserId());
        model.addAttribute("inventory", inventory);

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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);

        Product product = new Product(name, description, null, price);
        productRepository.insertProduct(product);
        
        Inventory inventory = new Inventory(product.getProductId(), quantity, location, user.getUserId());
        inventoryRepository.insertInventory(inventory);

        return "redirect:/inventory";
    }
}