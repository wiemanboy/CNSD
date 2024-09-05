package com.wiemanboy.cnsdbankapplication.domain;

import com.wiemanboy.cnsdbankapplication.domain.enums.AccountStatus;
import com.wiemanboy.cnsdbankapplication.domain.exception.TooLittleCustomersInBankAccountException;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * BankAccount has multiple customers associated with it, needs at least one customer to be created.
 * Also includes the value of the account and the status of the account.
 */
@Entity
@Getter
public class BankAccount extends TimeStamped {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToMany
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
