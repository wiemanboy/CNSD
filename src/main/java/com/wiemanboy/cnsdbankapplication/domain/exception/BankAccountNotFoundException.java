package com.wiemanboy.cnsdbankapplication.domain.exception;

import java.util.UUID;

public class BankAccountNotFoundException extends RuntimeException {
    private final UUID id;

    public BankAccountNotFoundException(UUID bankAccountId) {
        super(String.format("Bank account with id %s not found", bankAccountId));
        this.id = bankAccountId;
    }

    public UUID getId() {
        return id;
    }
}
