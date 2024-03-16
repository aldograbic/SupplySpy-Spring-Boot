package com.project.SupplySpy.controllers;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.SupplySpy.repositories.sales_orders.SalesOrderRepository;

@RestController
public class ChartDataController {

    @Autowired
    private SalesOrderRepository salesOrderRepository;
    
    @GetMapping("/api/chart-data")
    Map<String, Object> getChartData() {
        List<Map<String, Object>> salesOrdersLastFiveWeeks = salesOrderRepository.getSalesOrdersLastFiveWeeks();
         BigDecimal maxPayment = salesOrderRepository.findMaxPayment();
         BigDecimal yAxisMax = maxPayment != null ? maxPayment : BigDecimal.ZERO;

         return Map.of(
            "lineChart", Map.of(
                    "salesData", salesOrdersLastFiveWeeks.stream().map(map -> map.get("order_count")).collect(Collectors.toList()),
                    "categories", salesOrdersLastFiveWeeks.stream().map(map -> map.get("week_start").toString()).collect(Collectors.toList()),
                    "yAxisMax", yAxisMax
            )
        );
    }
}