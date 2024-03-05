package com.project.SupplySpy.repositories.order_items;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.project.SupplySpy.classes.OrderItem;
import com.project.SupplySpy.repositories.products.ProductRepository;
import com.project.SupplySpy.repositories.sales_orders.SalesOrderRepository;

@Repository
public class JdbcOrderItemRepository implements OrderItemRepository{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private SalesOrderRepository salesOrderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<OrderItem> getOrderItemsForOrderByOrderId(int orderId) {
        String sql = "SELECT order_item_id, order_id, product_id, quantity, price FROM order_items WHERE order_id = ?";
        return jdbcTemplate.query(sql, new OrderItemRowMapper(salesOrderRepository, productRepository), orderId);
    }
}