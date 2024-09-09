package com.wiemanboy.cnsdbankapplication.domain;

import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors(chain = true)
public class BankAccountBuilder {
    private Customer customer = new Customer("test");

    public BankAccount build() {
        return new BankAccount(customer);
    }
}
