package com.wiemanboy.cnsdbankapplication.application;

import com.wiemanboy.cnsdbankapplication.data.BankAccountRepository;
import com.wiemanboy.cnsdbankapplication.domain.BankAccount;
import com.wiemanboy.cnsdbankapplication.domain.BankAccountBuilder;
import com.wiemanboy.cnsdbankapplication.domain.Customer;
import com.wiemanboy.cnsdbankapplication.domain.enums.AccountStatus;
import com.wiemanboy.cnsdbankapplication.domain.exception.BankAccountNotFoundException;
import com.wiemanboy.cnsdbankapplication.domain.exception.TooLittleCustomersInBankAccountException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.context.annotation.Description;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

class BankAccountServiceTest {
    private BankAccountService bankAccountService;
    private CustomerService customerService;
    private BankAccountRepository bankAccountRepository;

    @BeforeEach
    public void setUp() {
        customerService = Mockito.mock(CustomerService.class);
        bankAccountRepository = Mockito.mock(BankAccountRepository.class);
        bankAccountService = new BankAccountService(
                customerService,
                bankAccountRepository
        );
    }

    @Test
    @Description("Test if BankAccount is created and saved")
    public void createBankAccountForCustomer() {
        BankAccount bankAccount = (new BankAccountBuilder()).build();

        Mockito.when(customerService.getCustomerById(null)).thenReturn(bankAccount.getCustomers().getFirst());
        Mockito.when(bankAccountRepository.save(bankAccount)).thenReturn(bankAccount);

        bankAccountService.createBankAccountForCustomer(null);

        verify(bankAccountRepository).save(Mockito.any());
    }

    @Test
    @Description("Test if BankAccount is retrieved by ID")
    public void getBankAccountById() {
        BankAccount bankAccount = (new BankAccountBuilder()).build();

        Mockito.when(bankAccountRepository.findBankAccountById(null)).thenReturn(Optional.of(bankAccount));

        bankAccountService.getBankAccountById(null);

        verify(bankAccountRepository).findBankAccountById(null);
    }

    @Test
    @Description("Test if BankAccount is not retrieved by ID throws exception")
    public void getBankAccountByIdThrowsException() {
        Mockito.when(bankAccountRepository.findBankAccountById(null)).thenReturn(Optional.empty());

        assertThrows(
                BankAccountNotFoundException.class,
                () -> bankAccountService.getBankAccountById(null)
        );
        verify(bankAccountRepository).findBankAccountById(null);
    }

    @Test
    @Description("Test if BankAccounts are retrieved")
    public void getBankAccounts() {
        bankAccountService.getBankAccounts();

        verify(bankAccountRepository).findAll();
    }

    @Test
    @Description("Test if BankAccount is updated by ID")
    public void updateBankAccountById() {
        BankAccount bankAccount = (new BankAccountBuilder()).build();

        Mockito.when(bankAccountRepository.findBankAccountById(null)).thenReturn(Optional.of(bankAccount));
        Mockito.when(bankAccountRepository.save(bankAccount)).thenReturn(bankAccount);

        BankAccount result = bankAccountService.updateBankAccountById(null, AccountStatus.SUSPENDED);

        assertEquals(AccountStatus.SUSPENDED, result.getStatus());
        verify(bankAccountRepository).save(Mockito.any());
    }

    @Test
    @Description("Test if Customer is added to BankAccount")
    public void addCustomerToBankAccount() {
        BankAccount bankAccount = (new BankAccountBuilder()).build();

        Mockito.when(bankAccountRepository.findBankAccountById(null)).thenReturn(Optional.of(bankAccount));
        Mockito.when(customerService.getCustomerById(null)).thenReturn(bankAccount.getCustomers().getFirst());
        Mockito.when(bankAccountRepository.save(bankAccount)).thenReturn(bankAccount);

        bankAccountService.addCustomerToBankAccount(null, null);

        assertEquals(2, bankAccount.getCustomers().size());
        verify(bankAccountRepository).save(Mockito.any());
    }

    @Test
    @Description("Test if Customer is removed from BankAccount")
    public void removeCustomerFromBankAccount() {
        BankAccount bankAccount = (new BankAccountBuilder()).build();
        bankAccount.addCustomer(new Customer("test"));

        Mockito.when(bankAccountRepository.findBankAccountById(null)).thenReturn(Optional.of(bankAccount));
        Mockito.when(customerService.getCustomerById(null)).thenReturn(bankAccount.getCustomers().getFirst());
        Mockito.when(bankAccountRepository.save(bankAccount)).thenReturn(bankAccount);

        BankAccount result = bankAccountService.removeCustomerFromBankAccount(null, null);

        assertEquals(1, result.getCustomers().size());
        verify(bankAccountRepository).save(Mockito.any());
    }

    @Test
    @Description("Test if Customer is not removed from BankAccount and throws exception")
    public void removeCustomerFromBankAccountThrowsException() {
        BankAccount bankAccount = (new BankAccountBuilder()).build();

        Mockito.when(bankAccountRepository.findBankAccountById(null)).thenReturn(Optional.of(bankAccount));
        Mockito.when(customerService.getCustomerById(null)).thenReturn(bankAccount.getCustomers().getFirst());

        assertThrows(
                TooLittleCustomersInBankAccountException.class,
                () -> bankAccountService.removeCustomerFromBankAccount(null, null)
        );
        assertEquals(1, bankAccount.getCustomers().size());
        verify(bankAccountRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    @Description("Test if BankAccount is deleted by ID")
    public void deleteBankAccountById() {
        BankAccount bankAccount = (new BankAccountBuilder()).build();

        Mockito.when(bankAccountRepository.removeBankAccountById(null)).thenReturn(Optional.of(bankAccount));

        bankAccountService.deleteBankAccountById(null);

        verify(bankAccountRepository).removeBankAccountById(null);
    }

    @Test
    @Description("Test if BankAccount is deleted by ID returns null")
    public void deleteBankAccountByIdReturnsNull() {
        Mockito.when(bankAccountRepository.removeBankAccountById(null)).thenReturn(Optional.empty());

        BankAccount result = bankAccountService.deleteBankAccountById(null);

        assertNull(result);
        verify(bankAccountRepository).removeBankAccountById(null);
    }
}