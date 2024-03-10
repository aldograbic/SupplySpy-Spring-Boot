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
    public List<Inventory> getInventory(int page, int size) {
        String sql = "SELECT * FROM inventory INNER JOIN products ON inventory.product_id = products.product_id ORDER BY inventory.inventory_id ASC LIMIT ? OFFSET ?";
        int offset = (page - 1) * size;
        return jdbcTemplate.query(sql, new InventoryRowMapper(productRepository), new Object[]{size, offset});
    }

    @Override
    public void insertInventory(Inventory inventory) {
        String sql = "INSERT INTO inventory (product_id, quantity, location) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, inventory.getProductId(), inventory.getQuantity(), inventory.getLocation());
    }

    @Override
    public int getTotalInventoryCount() {
        String sql = "SELECT COUNT(*) FROM inventory";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
        if (count == null) {
            return 0;
        }
        return count;
    }

    @Override
    public int getNoQuantityInventoryCount() {
        String sql = "SELECT COUNT(*) FROM inventory WHERE quantity = 0";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
        if (count == null) {
            return 0;
        }
        return count;
    }

    @Override
    public void updateInventory(Inventory inventory) {
        String sql = "UPDATE inventory SET quantity = ?, location = ? WHERE inventory_id = ?";
        jdbcTemplate.update(sql, inventory.getQuantity(), inventory.getLocation(), inventory.getInventoryId());
    }

    @Override
    public void deleteInventory(Inventory inventory) {
        String sql = "DELETE FROM inventory WHERE inventory_id = ?";
        jdbcTemplate.update(sql, inventory.getInventoryId());
    }
}