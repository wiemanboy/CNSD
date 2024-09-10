package com.wiemanboy.cnsdbankapplication.presentation;

import com.wiemanboy.cnsdbankapplication.application.CustomerService;
import com.wiemanboy.cnsdbankapplication.domain.Customer;
import com.wiemanboy.cnsdbankapplication.presentation.dto.request.CustomerCreateDTO;
import com.wiemanboy.cnsdbankapplication.presentation.dto.request.CustomerUpdateDTO;
import com.wiemanboy.cnsdbankapplication.presentation.dto.response.CustomerDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Description;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CustomerControllerTest {
    private CustomerService customerService;
    private CustomerController customerController;

    @BeforeEach
    void setUp() {
        customerService = mock(CustomerService.class);
        customerController = new CustomerController(customerService);
    }

    @Test
    @Description("Test create customer returns a CustomerDTO")
    void testCreateCustomer() {
        Customer customer = new Customer("test");

        when(customerService.createCustomer("test")).thenReturn(customer);

        CustomerDTO result = customerController.createCustomer(new CustomerCreateDTO("test"));

        assertInstanceOf(CustomerDTO.class, result);
        assertEquals(customer.getName(), result.name());
    }

    @Test
    @Description("Test get customers returns a list of CustomerDTO")
    void testGetCustomers() {
        List<Customer> customers = List.of(
                new Customer("test1"),
                new Customer("test2")
        );

        when(customerService.getCustomers()).thenReturn(customers);

        List<CustomerDTO> result = customerController.getCustomers();

        assertEquals(customers.size(), result.size());
    }

    @Test
    @Description("Test get customer by id returns a CustomerDTO")
    void testGetCustomerById() {
        Customer customer = new Customer("test");

        when(customerService.getCustomerById(customer.getId())).thenReturn(customer);

        CustomerDTO result = customerController.getCustomerById(customer.getId());

        assertInstanceOf(CustomerDTO.class, result);
        assertEquals(customer.getName(), result.name());
    }

    @Test
    @Description("Test update customer by id returns a CustomerDTO")
    void testUpdateCustomerById() {
        Customer customer = new Customer("test");

        when(customerService.updateCustomerById(customer.getId(), "test")).thenReturn(customer);

        CustomerDTO result = customerController.updateCustomerById(customer.getId(), new CustomerUpdateDTO("test"));

        assertInstanceOf(CustomerDTO.class, result);
        assertEquals(customer.getName(), result.name());
    }

    @Test
    @Description("Test delete customer by id returns a CustomerDTO")
    void testDeleteCustomerById() {
        Customer customer = new Customer("test");

        when(customerService.deleteCustomerById(customer.getId())).thenReturn(customer);

        CustomerDTO result = customerController.deleteCustomerById(customer.getId());

        assertInstanceOf(CustomerDTO.class, result);
        assertEquals(customer.getName(), result.name());
    }

    @Test
    @Description("Test delete customer by id returns null")
    void testDeleteCustomerByIdReturnsNull() {
        CustomerDTO result = customerController.deleteCustomerById(UUID.randomUUID());

        assertNull(result);
    }
}