package com.wiemanboy.cnsdbankapplication.domain;

import com.wiemanboy.cnsdbankapplication.domain.enums.AccountStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * BankAccount has multiple customers associated with it, needs at least one customer to be created.
 * Also includes the value of the account and the status of the account.
 */
public class BankAccount {
    private List<Customer> customers = new ArrayList<Customer>();
    private AccountStatus status = AccountStatus.OPEN;
    private int value = 0;

    public BankAccount(Customer customer) {
        addCustomer(customer);
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public int getValue() {
        return value;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void removeCustomer(Customer customer) {
        if (customers.size() <= 1) {
            throw new RuntimeException("BankAccount needs at least 1 customer");
        }
        customers.remove(customer);
    }
}
