package com.wiemanboy.cnsdbankapplication.domain;

import com.wiemanboy.cnsdbankapplication.domain.exception.TooLittleCustomersInBankAccountException;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Description;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BankAccountTest {

    @Test
    @Description("Test if a customer can be added to a bank account")
    public void testAddCustomer() {
        BankAccount bankAccount = (new BankAccountBuilder()).build();
        Customer customer = new Customer("test");

        bankAccount.addCustomer(customer);

        assertEquals(2, bankAccount.getCustomers().size());
    }

    @Test
    @Description("Test if a customer can be removed from a bank account")
    public void testRemoveCustomer() {
        BankAccount bankAccount = (new BankAccountBuilder()).build();
        Customer customer = new Customer("test");

        bankAccount.addCustomer(customer);
        bankAccount.removeCustomer(customer);

        assertEquals(1, bankAccount.getCustomers().size());
    }

    @Test
    @Description("Test if method throws error when customer is removed from a bank account with only one customer")
    public void testRemoveCustomerThrowsTooLittleCustomersInBankAccountException() {
        BankAccount bankAccount = (new BankAccountBuilder()).build();

        assertThrows(TooLittleCustomersInBankAccountException.class, () ->
                bankAccount.removeCustomer(bankAccount.getCustomers().getFirst()));
    }
}