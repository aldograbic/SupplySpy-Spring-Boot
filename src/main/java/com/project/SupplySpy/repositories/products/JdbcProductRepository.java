package com.project.SupplySpy.repositories.products;

import java.sql.PreparedStatement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.project.SupplySpy.classes.Product;

@Repository
public class JdbcProductRepository implements ProductRepository{
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Product getProductByProductId(int productId) {
        String sql = "SELECT product_id, name, description, image, price, created_at FROM products WHERE product_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new ProductRowMapper(), productId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @SuppressWarnings("null")
    @Override
    public void insertProduct(Product product) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO products (name, description, image, price) VALUES (?, ?, ?, ?)", new String[]{"product_id"});
            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setString(3, product.getImage());
            ps.setBigDecimal(4, product.getPrice());
            return ps;
        }, keyHolder);
        product.setProductId(keyHolder.getKey().intValue());
    }
}