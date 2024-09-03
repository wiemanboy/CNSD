package com.wiemanboy.cnsdbankapplication.domain.exception;

public class TooLittleCustomersInBankAccountException extends RuntimeException {
    private final int amount;

    public TooLittleCustomersInBankAccountException(int amount) {
        super(String.format("Bank account needs at least %s customers", amount));
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }
}
