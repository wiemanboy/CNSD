package com.wiemanboy.cnsdbankapplication.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import java.util.List;
import java.util.UUID;

@Entity
public class Customer {
    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    @ManyToMany
    private List<BankAccount> bankAccounts;

    protected Customer() {
    }

    public Customer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
