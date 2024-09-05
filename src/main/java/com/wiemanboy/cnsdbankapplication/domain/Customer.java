package com.wiemanboy.cnsdbankapplication.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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
    private List<BankAccount> bankAccounts;

    protected Customer() {
    }

    public Customer(String name) {
        this.name = name;
    }
}
