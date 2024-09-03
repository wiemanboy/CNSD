package com.wiemanboy.cnsdbankapplication.application;

import com.wiemanboy.cnsdbankapplication.data.CustomerRepository;
import com.wiemanboy.cnsdbankapplication.domain.Customer;
import com.wiemanboy.cnsdbankapplication.domain.exception.CustomerNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer createCustomer(String name) {
        Customer customer = new Customer(name);

        customerRepository.save(customer);
        return new Customer(name);
    }

    public Customer getCustomerById(UUID id) {
        return customerRepository.findCustomerById(id).orElseThrow(
                () -> new CustomerNotFoundException(id)
        );
    }

    public List<Customer> getCustomers() {
        return customerRepository.findCustomers();
    }

    public Customer updateCustomerById(UUID id, String name) {
        Customer customer = getCustomerById(id);

        customer.setName(name);

        customerRepository.save(customer);
        return customer;
    }

    public Customer deleteCustomerById(UUID id) {
        return customerRepository.removeCustomerById(id).orElse(null);
    }
}
