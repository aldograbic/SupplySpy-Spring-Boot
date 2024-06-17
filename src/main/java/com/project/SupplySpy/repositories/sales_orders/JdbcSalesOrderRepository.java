package com.project.SupplySpy.repositories.sales_orders;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.project.SupplySpy.classes.SalesOrder;
import com.project.SupplySpy.repositories.customers.CustomerRepository;

@Repository
public class JdbcSalesOrderRepository implements SalesOrderRepository{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CustomerRepository customerRepository;
    
    @Override
    public List<SalesOrder> getSalesOrders(int page, int size) {
        String sql = "SELECT * FROM sales_orders INNER JOIN customers ON sales_orders.customer_id = customers.customer_id ORDER BY sales_orders.order_id ASC LIMIT ? OFFSET ?";
        int offset = (page - 1) * size;
        return jdbcTemplate.query(sql, new SalesOrderRowMapper(customerRepository), new Object[]{size, offset});
    }

    @Override
    public int getTotalSalesOrdersCount() {
        String sql = "SELECT COUNT(*) FROM sales_orders";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
        if (count == null) {
            return 0;
        }
        return count;
    }

    @Override
    public int getCompletedSalesOrdersCount() {
        String sql = "SELECT COUNT(*) FROM sales_orders WHERE status = 'Completed'";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
        if (count == null) {
            return 0;
        }
        return count;
    }

    @Override
    public SalesOrder findSalesOrderByOrderId(int orderId) {
        String sql = "SELECT * FROM sales_orders WHERE order_id = ?";
        List<SalesOrder> salesOrders = jdbcTemplate.query(sql, new SalesOrderRowMapper(customerRepository), orderId);
        return salesOrders.isEmpty() ? null : salesOrders.get(0);
    }

    @Override
    public void insertSalesOrder(SalesOrder salesOrder) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO sales_orders (customer_id) VALUES (?)", new String[]{"order_id"});
            ps.setInt(1, salesOrder.getCustomerId());
            return ps;
        }, keyHolder);
        salesOrder.setOrderId(keyHolder.getKey().intValue());
    }

    @Override
    public List<Map<String, Object>> getSalesOrdersLastFiveWeeks() {
        String sql = """
        SELECT DATE_FORMAT(order_date, '%Y-%u') AS week_start, COUNT(*) AS order_count \
        FROM sales_orders \
        WHERE order_date >= CURDATE() - INTERVAL 5 WEEK GROUP BY week_start ORDER BY week_start ASC\
        """;
        return jdbcTemplate.queryForList(sql);
    }

    @Override
    public BigDecimal findMaxPayment() {
    String sql = """
                 SELECT MAX(total_payment) as max_payment \
                 FROM ( \
                     SELECT o.order_id, SUM(oi.price * oi.quantity) AS total_payment \
                     FROM sales_orders o \
                     JOIN order_items oi ON o.order_id = oi.order_id \
                     GROUP BY o.order_id \
                 ) AS order_payments;\
                 """;
        return jdbcTemplate.queryForObject(sql, BigDecimal.class);
    }
}