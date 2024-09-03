package com.wiemanboy.cnsdbankapplication.domain.exception;

import java.util.UUID;

public class CustomerNotFoundException extends RuntimeException {
    private final UUID id;

    public CustomerNotFoundException(UUID customerId) {
        super(String.format("Customer with id %s not found", customerId));
        this.id = customerId;
    }

    public UUID getId() {
        return id;
    }
}
