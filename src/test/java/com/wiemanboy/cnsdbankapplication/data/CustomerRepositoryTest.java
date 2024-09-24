package com.wiemanboy.cnsdbankapplication.data;

import com.wiemanboy.cnsdbankapplication.domain.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CustomerRepositoryTest {
    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        customerRepository.deleteAll();
    }

    @Test
    void testFindCustomerById() {
        Customer customer = new Customer("test");
        customer = customerRepository.save(customer);

        Optional<Customer> retrievedCustomer = customerRepository.findCustomerById(customer.getId());

        assertTrue(retrievedCustomer.isPresent());
        assertEquals(customer.getId(), retrievedCustomer.get().getId());
    }

    @Test
    void testFindAllCustomers() {
        customerRepository.save(new Customer("test1"));
        customerRepository.save(new Customer("test2"));

        List<Customer> customers = customerRepository.findAll();

        assertEquals(2, customers.size());
    }

    @Test
    void testRemoveCustomerById() {
        Customer customer = new Customer("test");
        customer = customerRepository.save(customer);

        customerRepository.removeCustomerById(customer.getId());

        Optional<Customer> retrievedCustomer = customerRepository.findCustomerById(customer.getId());

        assertFalse(retrievedCustomer.isPresent());
    }

    @Test
    void testSaveCustomer() {
        Customer customer = new Customer("test");

        Customer savedCustomer = customerRepository.save(customer);

        assertEquals(customer.getId(), savedCustomer.getId());
    }
}