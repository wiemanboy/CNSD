package com.wiemanboy.cnsdbankapplication.application;

import com.wiemanboy.cnsdbankapplication.domain.BankAccount;
import com.wiemanboy.cnsdbankapplication.domain.Customer;

import java.util.List;
import java.util.UUID;

public class BankAccountService {
    public BankAccount createBankAccountForCustomer(UUID customerId) {
        return new BankAccount(new Customer("placeholder"));
    }

    public BankAccount getBankAccountById(UUID bankAccountId) {
        return null;
    }

    public List<BankAccount> getBankAccounts() {
        return null;
    }

    public BankAccount updateBankAccountById(UUID bankAccountId, UUID customerId) {
        return null;
    }

    public BankAccount addCustomerToBankAccount(UUID bankAccountId, UUID customerId) {
        return null;
    }

    public BankAccount removeCustomerFromBankAccount(UUID bankAccountId, UUID customerId) {
        return null;
    }

    public BankAccount removeBankAccountById(UUID bankAccountId) {
        return null;
    }
}
