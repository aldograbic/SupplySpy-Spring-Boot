package com.project.SupplySpy.repositories.inventory;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import com.project.SupplySpy.classes.Inventory;
import com.project.SupplySpy.classes.Product;
import com.project.SupplySpy.repositories.products.ProductRepository;

public class InventoryRowMapper implements RowMapper<Inventory>{

    private ProductRepository productRepository;

    public InventoryRowMapper(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Nullable
    public Inventory mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
        Inventory inventory = new Inventory();
        inventory.setInventoryId(rs.getInt("inventory_id"));
        inventory.setProductId(rs.getInt("product_id"));
        inventory.setQuantity(rs.getInt("quantity"));
        inventory.setLocation(rs.getString("location"));
        inventory.setUserId(rs.getInt("user_id"));
        inventory.setUpdatedAt(rs.getTimestamp("updated_at"));

        int productId = rs.getInt("product_id");
        Product product = productRepository.getProductByProductId(productId);
        inventory.setProduct(product);
        return inventory;
    }
}