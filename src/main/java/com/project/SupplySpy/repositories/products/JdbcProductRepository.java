package com.project.SupplySpy.repositories.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.project.SupplySpy.classes.Product;

@Repository
public class JdbcProductRepository implements ProductRepository{
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Product getProductByProductId(int productId) {
        String sql = "SELECT productId, name, description, image, price, createdAt FROM products WHERE product_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new ProductRowMapper(), productId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}