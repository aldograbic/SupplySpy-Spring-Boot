package com.project.SupplySpy.repositories.customers;

import java.util.List;

import com.project.SupplySpy.classes.Customer;

public interface CustomerRepository {
    List<Customer> getCustomers(int page, int size);
    int getTotalCustomersCount();
    Customer findByCustomerId(int customerId);
    void updateCustomer(Customer customer);
}