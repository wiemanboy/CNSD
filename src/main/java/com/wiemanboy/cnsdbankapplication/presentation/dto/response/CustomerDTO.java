package com.wiemanboy.cnsdbankapplication.presentation.dto.response;

import com.wiemanboy.cnsdbankapplication.domain.Customer;

import java.util.List;
import java.util.UUID;

public record CustomerDTO(
        UUID id,
        String name
) {
    public static CustomerDTO from(Customer customer) {
        return new CustomerDTO(customer.getId(), customer.getName());
    }

    public static List<CustomerDTO> from(List<Customer> customers) {
        return customers.stream().map(CustomerDTO::from).toList();
    }
}
