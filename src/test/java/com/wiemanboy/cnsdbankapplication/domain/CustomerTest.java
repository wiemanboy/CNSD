package com.wiemanboy.cnsdbankapplication.domain;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Description;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerTest {

    @Test
    @Description("Test Customer constructor")
    public void testCustomer() {
        Customer customer = new Customer("John Doe");
        assertEquals("John Doe", customer.getName());
    }
}