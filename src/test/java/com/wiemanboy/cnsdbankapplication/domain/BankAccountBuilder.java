package com.wiemanboy.cnsdbankapplication.domain;

import com.wiemanboy.cnsdbankapplication.domain.enums.AccountStatus;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors(chain = true)
public class BankAccountBuilder {
    private Customer customer = new Customer("test");
    private AccountStatus status = AccountStatus.OPEN;

    public BankAccount build() {
        BankAccount bankAccount = new BankAccount(customer);

        bankAccount.setStatus(status);

        return bankAccount;
    }
}
