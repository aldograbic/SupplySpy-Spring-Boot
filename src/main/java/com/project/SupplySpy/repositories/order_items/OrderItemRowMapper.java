package com.project.SupplySpy.repositories.order_items;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import com.project.SupplySpy.classes.OrderItem;
import com.project.SupplySpy.classes.Product;
import com.project.SupplySpy.classes.SalesOrder;
import com.project.SupplySpy.repositories.products.ProductRepository;
import com.project.SupplySpy.repositories.sales_orders.SalesOrderRepository;

public class OrderItemRowMapper implements RowMapper<OrderItem>{

    private SalesOrderRepository salesOrderRepository;
    private ProductRepository productRepository;

    public OrderItemRowMapper(SalesOrderRepository salesOrderRepository, ProductRepository productRepository) {
        this.salesOrderRepository = salesOrderRepository;
        this.productRepository = productRepository;
    }

    @Override
    @Nullable
    public OrderItem mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderItemId(rs.getInt("order_item_id"));
        orderItem.setOrderId(rs.getInt("order_id"));
        orderItem.setProductId(rs.getInt("product_id"));
        orderItem.setQuantity(rs.getInt("quantity"));
        orderItem.setPrice(rs.getBigDecimal("price"));

        int orderId = rs.getInt("order_id");
        SalesOrder salesOrder = salesOrderRepository.findSalesOrderByOrderId(orderId);
        orderItem.setSalesOrder(salesOrder);

        int productId = rs.getInt("product_id");
        Product product = productRepository.getProductByProductId(productId);
        orderItem.setProduct(product);

        return orderItem;
    }
}