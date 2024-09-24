package com.wiemanboy.cnsdbankapplication.data;

import com.wiemanboy.cnsdbankapplication.domain.BankAccount;
import com.wiemanboy.cnsdbankapplication.domain.BankAccountBuilder;
import com.wiemanboy.cnsdbankapplication.domain.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BankAccountRepositoryTest {
    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        bankAccountRepository.deleteAll();
        customerRepository.deleteAll();
    }

    @Test
    void testFindBankAccountById() {
        Customer customer = customerRepository.save(new Customer("test"));
        BankAccount bankAccount = bankAccountRepository.save(new BankAccountBuilder().setCustomer(customer).build());

        Optional<BankAccount> retrievedBankAccount = bankAccountRepository.findBankAccountById(bankAccount.getId());

        assertTrue(retrievedBankAccount.isPresent());
        assertEquals(bankAccount.getId(), retrievedBankAccount.get().getId());
    }

    @Test
    void testFindAllBankAccounts() {
        Customer customer = customerRepository.save(new Customer("test"));
        bankAccountRepository.save(new BankAccountBuilder().setCustomer(customer).build());
        bankAccountRepository.save(new BankAccountBuilder().setCustomer(customer).build());

        List<BankAccount> bankAccounts = bankAccountRepository.findAll();

        assertEquals(2, bankAccounts.size());
    }

    @Test
    void testDeleteById() {
        Customer customer = customerRepository.save(new Customer("test"));
        BankAccount bankAccount = bankAccountRepository.save(new BankAccountBuilder().setCustomer(customer).build());

        bankAccountRepository.removeBankAccountById(bankAccount.getId());

        Optional<BankAccount> retrievedBankAccount = bankAccountRepository.findBankAccountById(bankAccount.getId());

        assertFalse(retrievedBankAccount.isPresent());
    }

    @Test
    void testSaveBankAccount() {
        Customer customer = customerRepository.save(new Customer("test"));
        BankAccount bankAccount = new BankAccountBuilder().setCustomer(customer).build();

        BankAccount savedBankAccount = bankAccountRepository.save(bankAccount);

        assertEquals(bankAccount.getId(), savedBankAccount.getId());
    }
}