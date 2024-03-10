package com.project.SupplySpy.repositories.sales_orders;

import java.util.List;

import com.project.SupplySpy.classes.SalesOrder;

public interface SalesOrderRepository {
    List<SalesOrder> getSalesOrders(int page, int size);
    int getTotalSalesOrdersCount();
    int getCompletedSalesOrdersCount();
    SalesOrder findSalesOrderByOrderId(int orderId);
    void insertSalesOrder(SalesOrder salesOrder);
}