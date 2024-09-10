package com.wiemanboy.cnsdbankapplication.application;

import com.wiemanboy.cnsdbankapplication.data.CustomerRepository;
import com.wiemanboy.cnsdbankapplication.domain.Customer;
import com.wiemanboy.cnsdbankapplication.domain.exception.CustomerNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.context.annotation.Description;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CustomerServiceTest {
    private CustomerService customerService;
    private CustomerRepository customerRepository;

    @BeforeEach
    public void setUp() {
        customerRepository = Mockito.mock(CustomerRepository.class);
        customerService = new CustomerService(
                customerRepository
        );
    }

    @Test
    public void testCreateCustomer() {
        Customer result = customerService.createCustomer("test");

        verify(customerRepository).save(Mockito.any());
        assertNotNull(result);
    }

    @Test
    @Description("Test if Customer is retrieved by ID")
    public void testGetCustomerById() {
        Customer customer = new Customer("test");

        when(customerRepository.findCustomerById(null)).thenReturn(Optional.of(customer));

        Customer result = customerService.getCustomerById(null);

        verify(customerRepository).findCustomerById(null);
        assertNotNull(result);
    }

    @Test
    @Description("Test if Customer is not retrieved by ID and throws exception")
    public void testGetCustomerByIdThrowsException() {
        when(customerRepository.findCustomerById(null)).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> customerService.getCustomerById(null));
    }

    @Test
    @Description("Test if Customers are retrieved")
    public void testGetCustomers() {
        List<Customer> customers = List.of(
                new Customer("test"),
                new Customer("test2")
        );

        when(customerRepository.findAll()).thenReturn(customers);

        List<Customer> result = customerService.getCustomers();

        verify(customerRepository).findAll();
        assertEquals(2, result.size());
    }

    @Test
    @Description("Test if Customer is updated by ID")
    public void testUpdateCustomerById() {
        Customer customer = new Customer("test");

        when(customerRepository.findCustomerById(null)).thenReturn(Optional.of(customer));

        Customer result = customerService.updateCustomerById(null, "newName");

        verify(customerRepository).save(Mockito.any());
        assertEquals("newName", result.getName());
    }

    @Test
    @Description("Test if Customer is deleted by ID")
    public void testDeleteCustomerById() {
        Customer customer = new Customer("test");
        when(customerRepository.removeCustomerById(null)).thenReturn(Optional.of(customer));

        Customer result = customerService.deleteCustomerById(null);

        verify(customerRepository).removeCustomerById(null);
        assertNotNull(result);
    }

    @Test
    @Description("Test if Customer is not deleted by ID and returns null")
    public void testDeleteCustomerByIdReturnsNull() {
        when(customerRepository.removeCustomerById(null)).thenReturn(Optional.empty());

        Customer result = customerService.deleteCustomerById(null);

        assertNull(result);
    }
}