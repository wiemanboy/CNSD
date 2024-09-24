package com.wiemanboy.cnsdbankapplication.domain.exception;

import lombok.Getter;

import java.util.UUID;

@Getter
public class BankAccountNotFoundException extends RuntimeException {
    private final UUID id;

    public BankAccountNotFoundException(UUID bankAccountId) {
        super(String.format("Bank account with id %s not found", bankAccountId));
        this.id = bankAccountId;
    }
}
