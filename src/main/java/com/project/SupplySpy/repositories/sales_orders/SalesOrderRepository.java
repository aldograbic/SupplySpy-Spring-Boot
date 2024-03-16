package com.project.SupplySpy.repositories.sales_orders;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.project.SupplySpy.classes.SalesOrder;

public interface SalesOrderRepository {
    List<SalesOrder> getSalesOrders(int page, int size);
    int getTotalSalesOrdersCount();
    int getCompletedSalesOrdersCount();
    SalesOrder findSalesOrderByOrderId(int orderId);
    void insertSalesOrder(SalesOrder salesOrder);
    List<Map<String, Object>> getSalesOrdersLastFiveWeeks();
    BigDecimal findMaxPayment();
}