package com.wiemanboy.cnsdbankapplication.application;

import com.wiemanboy.cnsdbankapplication.domain.Customer;

import java.util.List;
import java.util.UUID;

public class CustomerService {

    public Customer createCustomer(String name) {
        return new Customer(name);
    }

    public Customer getCustomerById(UUID id) {
        return null;
    }

    public List<Customer> getCustomers() {
        return null;
    }

    public Customer updateCustomerById(UUID id, String name) {
        return null;
    }

    public Customer deleteCustomerById(UUID id) {
        return null;
    }
}
