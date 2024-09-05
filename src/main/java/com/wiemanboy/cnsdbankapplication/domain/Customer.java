package com.wiemanboy.cnsdbankapplication.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
public class Customer extends DBObject {
    @Setter
    private String name;

    @ManyToMany
    @JoinTable(
            name = "bank_account_customers",
            joinColumns = @JoinColumn(name = "bank_account_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id")
    )
    private List<BankAccount> bankAccounts;

    protected Customer() {
    }

    public Customer(String name) {
        this.name = name;
    }
}
