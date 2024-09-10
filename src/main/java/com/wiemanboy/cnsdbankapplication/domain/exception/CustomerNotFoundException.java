package com.wiemanboy.cnsdbankapplication.domain.exception;

import lombok.Getter;

import java.util.UUID;

@Getter
public class CustomerNotFoundException extends RuntimeException {
    private final UUID id;

    public CustomerNotFoundException(UUID customerId) {
        super(String.format("Customer with id %s not found", customerId));
        this.id = customerId;
    }
}
