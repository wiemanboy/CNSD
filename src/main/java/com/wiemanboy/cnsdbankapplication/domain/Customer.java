package com.wiemanboy.cnsdbankapplication.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
public class Customer extends TimeStamped {
    @Id
    @GeneratedValue
    private UUID id;

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
