package com.project.SupplySpy.controllers;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
                                @RequestParam(defaultValue = "1") int page,
                                @RequestParam(defaultValue = "10") int size) {

        List<Inventory> inventory = inventoryRepository.getInventory(page, size);
        model.addAttribute("inventory", inventory);

        int totalItems = inventoryRepository.getTotalInventoryCount();
        int totalPages = (int) Math.ceil((double) totalItems / size);
        totalPages = Math.max(totalPages, 1);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);

        return "inventory";
    }

    @GetMapping("/inventory/search")
    public ResponseEntity<List<Inventory>> searchInventory(@RequestParam String query) {
        List<Inventory> searchResults = inventoryRepository.searchInventory(query);
        return ResponseEntity.ok().body(searchResults);
    }

    @PostMapping("/insertInventory")
    @Transactional
    public String insertInventory(@RequestParam String name, 
                                  @RequestParam String description, 
                                  @RequestParam MultipartFile image,
                                  @RequestParam BigDecimal price, 
                                  @RequestParam String location, 
                                  @RequestParam int quantity,
                                  RedirectAttributes redirectAttributes) {

        try {
            Product product = new Product(name, description, null, price);
            productRepository.insertProduct(product);
        
            Inventory inventory = new Inventory(product.getProductId(), quantity, location);
            inventoryRepository.insertInventory(inventory);

            redirectAttributes.addFlashAttribute("successMessage", "Product successfully added.");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "There was a problem with adding to inventory. Please try again.");
        }
        
        return "redirect:/inventory";
    }

    @PostMapping("/updateInventory")
    public String updateInventory(@ModelAttribute Inventory inventory, RedirectAttributes redirectAttributes) {

        try {
            inventoryRepository.updateInventory(inventory);
            redirectAttributes.addFlashAttribute("successMessage", "Inventory successfully updated.");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "There was a problem updating inventory. Please try again.");
        }

        return "redirect:/inventory";
    }

    @PostMapping("/deleteInventory")
    public String deleteInventory(@ModelAttribute Inventory inventory, RedirectAttributes redirectAttributes) {

        try {
            inventoryRepository.deleteInventory(inventory);
            redirectAttributes.addFlashAttribute("successMessage", "Inventory successfully deleted.");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "There was a problem deleting the inventory. Please try again.");
        }

        return "redirect:/inventory";
    }
}