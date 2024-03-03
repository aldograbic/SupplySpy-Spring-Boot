package com.project.SupplySpy.repositories.sales_orders;

import java.util.List;

import com.project.SupplySpy.classes.SalesOrder;

public interface SalesOrderRepository {
    List<SalesOrder> getSalesOrders(int page, int size);
    int getTotalSalesOrdersCount();
}