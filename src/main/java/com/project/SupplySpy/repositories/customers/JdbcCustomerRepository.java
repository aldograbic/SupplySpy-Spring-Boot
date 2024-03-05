package com.project.SupplySpy.repositories.customers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.project.SupplySpy.classes.Customer;

@Repository
public class JdbcCustomerRepository implements CustomerRepository{

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public List<Customer> getCustomers(int page, int size) {
        String sql = "SELECT customer_id, name, email, phone, address, created_at FROM customers ORDER BY customer_id ASC LIMIT ? OFFSET ?";
        int offset = (page - 1) * size;
        return jdbcTemplate.query(sql, new CustomerRowMapper(), new Object[]{size, offset});
    }

    @Override
    public int getTotalCustomersCount() {
        String sql = "SELECT COUNT(*) FROM customers";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
        if (count == null) {
            return 0;
        }
        return count;
    }

    @Override
    public Customer findByCustomerId(int customerId) {
        String sql = "SELECT customer_id, name, email, phone, address, created_at FROM customers WHERE customer_id = ?";
        List<Customer> customers = jdbcTemplate.query(sql, new CustomerRowMapper(), customerId);
        return customers.isEmpty() ? null : customers.get(0);
    }

    @Override
    public void updateCustomer(Customer customer) {
        String sql = "UPDATE customers SET name = ?, email = ?, phone = ?, address = ? WHERE customer_id = ?";
        jdbcTemplate.update(sql, customer.getName(), customer.getEmail(), customer.getPhone(), customer.getAddress(), customer.getCustomerId());
    }
}