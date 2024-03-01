package com.project.SupplySpy.repositories.inventory;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.project.SupplySpy.classes.Inventory;
import com.project.SupplySpy.repositories.products.ProductRepository;

@Repository
public class JdbcInventoryRepository implements InventoryRepository{
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Inventory> getInventoryForUserByUserId(int userId) {
        String sql = "SELECT * FROM inventory INNER JOIN products ON inventory.product_id = products.product_id WHERE user_id = ?";
        return jdbcTemplate.query(sql, new InventoryRowMapper(productRepository), userId);
    }

    @Override
    public void insertInventory(Inventory inventory) {
        String sql = "INSERT INTO inventory (product_id, quantity, location, user_id) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, inventory.getProductId(), inventory.getQuantity(), inventory.getLocation(), inventory.getUserId());
    }
}