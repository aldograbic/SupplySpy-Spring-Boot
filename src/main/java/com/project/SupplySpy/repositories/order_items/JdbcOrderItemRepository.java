package com.project.SupplySpy.repositories.order_items;

import java.math.BigDecimal;
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

    @Override
    public void insertOrderItem(OrderItem orderItem) {
        String sql = "INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, orderItem.getOrderId(), orderItem.getProductId(), orderItem.getQuantity(), orderItem.getPrice());
    }

    @Override
    public BigDecimal getTotalRevenue() {
        String sql = "SELECT SUM(quantity * price) FROM order_items INNER JOIN sales_orders ON order_items.order_id = sales_orders.order_id WHERE status = 'COMPLETED'";
        BigDecimal totalRevenue = jdbcTemplate.queryForObject(sql, BigDecimal.class);
        return totalRevenue == null ? BigDecimal.ZERO : totalRevenue;
    }
}