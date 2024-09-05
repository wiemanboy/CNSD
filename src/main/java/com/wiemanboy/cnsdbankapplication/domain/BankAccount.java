package com.wiemanboy.cnsdbankapplication.domain;

import com.wiemanboy.cnsdbankapplication.domain.enums.AccountStatus;
import com.wiemanboy.cnsdbankapplication.domain.exception.TooLittleCustomersInBankAccountException;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * BankAccount has multiple customers associated with it, needs at least one customer to be created.
 * Also includes the value of the account and the status of the account.
 */
@Entity
@Getter
public class BankAccount extends DBObject {
    @ManyToMany
    @JoinTable(
            name = "bank_account_customers",
            joinColumns = @JoinColumn(name = "bank_account_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id")
    )
    private List<Customer> customers = new ArrayList<Customer>();

    @Setter
    private AccountStatus status = AccountStatus.OPEN;
    private int value = 0;

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
