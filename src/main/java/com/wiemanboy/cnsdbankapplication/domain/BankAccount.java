package com.wiemanboy.cnsdbankapplication.domain;

import com.wiemanboy.cnsdbankapplication.domain.enums.AccountStatus;
import com.wiemanboy.cnsdbankapplication.domain.exception.TooLittleCustomersInBankAccountException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class BankAccount extends DBObject {
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "bank_account_customers",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "bank_account_id")
    )
    private final List<Customer> customers = new ArrayList<Customer>();
    private final int balance = 0;
    @Setter
    private AccountStatus status = AccountStatus.OPEN;

    protected BankAccount() {
    }

    public BankAccount(Customer customer) {
        addCustomer(customer);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void removeCustomer(Customer customer) {
        if (customers.size() <= 1) {
            throw new TooLittleCustomersInBankAccountException(1);
        }
        customers.remove(customer);
    }
}