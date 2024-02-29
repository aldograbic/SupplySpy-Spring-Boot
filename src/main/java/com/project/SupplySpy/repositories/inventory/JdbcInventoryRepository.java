package com.project.SupplySpy.repositories.inventory;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.project.SupplySpy.classes.Inventory;

@Repository
public class JdbcInventoryRepository implements InventoryRepository{
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Inventory> getInventoryForUserByUserId(int userId) {
        String sql = "SELECT products.name, products.image, quantity, location FROM inventory INNER JOIN products ON inventory.product_id = products.product_id WHERE user_id = ?";
        return jdbcTemplate.query(sql, new InventoryRowMapper(), userId);
    }
}
