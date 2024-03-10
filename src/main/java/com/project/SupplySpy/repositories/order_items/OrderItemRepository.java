package com.project.SupplySpy.repositories.order_items;

import java.math.BigDecimal;
import java.util.List;

import com.project.SupplySpy.classes.OrderItem;

public interface OrderItemRepository {
    List<OrderItem> getOrderItemsForOrderByOrderId(int orderId);
    void insertOrderItem(OrderItem orderItem);
    BigDecimal getTotalRevenue();
}