package com.wiemanboy.cnsdbankapplication.domain.exception;

import java.util.UUID;

public class BankAccountNotFoundException extends RuntimeException {
    public BankAccountNotFoundException(UUID bankAccountId) {
        super("Bank account with id " + bankAccountId + " not found");
    }
}
