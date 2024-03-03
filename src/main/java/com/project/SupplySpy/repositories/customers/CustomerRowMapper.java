package com.project.SupplySpy.repositories.customers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import com.project.SupplySpy.classes.Customer;

public class CustomerRowMapper implements RowMapper<Customer>{

    @Override
    @Nullable
    public Customer mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
        Customer customer = new Customer();
        customer.setCustomerId(rs.getInt("customer_id"));
        customer.setName(rs.getString("name"));
        customer.setEmail(rs.getString("email"));
        customer.setPhone(rs.getString("phone"));
        customer.setAddress(rs.getString("address"));
        customer.setCreatedAt(rs.getTimestamp("created_at"));

        return customer;
    }
}