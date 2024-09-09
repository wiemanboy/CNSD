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
    public void createCustomer() {
        customerService.createCustomer("test");

        verify(customerRepository).save(Mockito.any());
    }

    @Test
    @Description("Test if Customer is retrieved by ID")
    public void getCustomerById() {
        Customer customer = new Customer("test");

        Mockito.when(customerRepository.findCustomerById(null)).thenReturn(Optional.of(customer));

        customerService.getCustomerById(null);

        verify(customerRepository).findCustomerById(null);
    }

    @Test
    @Description("Test if Customer is not retrieved by ID and throws exception")
    public void getCustomerByIdThrowsException() {
        Mockito.when(customerRepository.findCustomerById(null)).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> customerService.getCustomerById(null));
    }

    @Test
    @Description("Test if Customers are retrieved")
    public void getCustomers() {
        Customer customer = new Customer("test");

        Mockito.when(customerRepository.findAll()).thenReturn(List.of(customer));

        customerService.getCustomers();

        verify(customerRepository).findAll();
    }

    @Test
    @Description("Test if Customer is updated by ID")
    public void updateCustomerById() {
        Customer customer = new Customer("test");

        Mockito.when(customerRepository.findCustomerById(null)).thenReturn(Optional.of(customer));

        Customer result = customerService.updateCustomerById(null, "newName");

        assertEquals("newName", result.getName());
        verify(customerRepository).save(Mockito.any());
    }

    @Test
    @Description("Test if Customer is deleted by ID")
    public void deleteCustomerById() {
        customerService.deleteCustomerById(null);

        verify(customerRepository).removeCustomerById(null);
    }

    @Test
    @Description("Test if Customer is not deleted by ID and returns null")
    public void deleteCustomerByIdReturnsNull() {
        Mockito.when(customerRepository.removeCustomerById(null)).thenReturn(Optional.empty());

        Customer result = customerService.deleteCustomerById(null);

        assertNull(result);
    }
}