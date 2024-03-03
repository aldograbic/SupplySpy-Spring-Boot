package com.project.SupplySpy.repositories.sales_orders;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import com.project.SupplySpy.classes.Customer;
import com.project.SupplySpy.classes.SalesOrder;
import com.project.SupplySpy.repositories.customers.CustomerRepository;

public class SalesOrderRowMapper implements RowMapper<SalesOrder> {

    private CustomerRepository customerRepository;

    public SalesOrderRowMapper(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    @Nullable
    public SalesOrder mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
        SalesOrder salesOrder = new SalesOrder();
        salesOrder.setOrderId(rs.getInt("order_id"));
        salesOrder.setCustomerId(rs.getInt("customer_id"));
        salesOrder.setOrderDate(rs.getTimestamp("order_date"));
        salesOrder.setStatus(rs.getString("status"));

        int customerId = rs.getInt("customer_id");
        Customer customer = customerRepository.findByCustomerId(customerId);
        salesOrder.setCustomer(customer);

        return salesOrder;
    }
}