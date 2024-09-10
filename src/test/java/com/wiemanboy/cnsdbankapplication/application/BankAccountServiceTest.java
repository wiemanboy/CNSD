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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    public void testCreateBankAccountForCustomer() {
        BankAccount bankAccount = (new BankAccountBuilder()).build();

        when(customerService.getCustomerById(null)).thenReturn(bankAccount.getCustomers().getFirst());
        when(bankAccountRepository.save(bankAccount)).thenReturn(bankAccount);

        BankAccount result = bankAccountService.createBankAccountForCustomer(null);

        verify(bankAccountRepository).save(Mockito.any());
        assertNotNull(result);
    }

    @Test
    @Description("Test if BankAccount is retrieved by ID")
    public void testGetBankAccountById() {
        BankAccount bankAccount = (new BankAccountBuilder()).build();

        when(bankAccountRepository.findBankAccountById(null)).thenReturn(Optional.of(bankAccount));

        BankAccount result = bankAccountService.getBankAccountById(null);

        verify(bankAccountRepository).findBankAccountById(null);
        assertNotNull(result);
    }

    @Test
    @Description("Test if BankAccount is not retrieved by ID throws exception")
    public void testGetBankAccountByIdThrowsException() {
        when(bankAccountRepository.findBankAccountById(null)).thenReturn(Optional.empty());

        assertThrows(
                BankAccountNotFoundException.class,
                () -> bankAccountService.getBankAccountById(null)
        );
        verify(bankAccountRepository).findBankAccountById(null);
    }

    @Test
    @Description("Test if BankAccounts are retrieved")
    public void testGetBankAccounts() {
        List<BankAccount> bankAccounts = List.of(
                (new BankAccountBuilder()).build(),
                (new BankAccountBuilder()).build()
        );

        when(bankAccountRepository.findAll()).thenReturn(bankAccounts);

        List<BankAccount> result = bankAccountService.getBankAccounts();

        verify(bankAccountRepository).findAll();
        assertEquals(2, result.size());
    }

    @Test
    @Description("Test if BankAccount is updated by ID")
    public void testUpdateBankAccountById() {
        BankAccount bankAccount = (new BankAccountBuilder()).build();

        when(bankAccountRepository.findBankAccountById(null)).thenReturn(Optional.of(bankAccount));
        when(bankAccountRepository.save(bankAccount)).thenReturn(bankAccount);

        BankAccount result = bankAccountService.updateBankAccountById(null, AccountStatus.SUSPENDED);

        assertEquals(AccountStatus.SUSPENDED, result.getStatus());
        verify(bankAccountRepository).save(Mockito.any());
    }

    @Test
    @Description("Test if Customer is added to BankAccount")
    public void testAddCustomerToBankAccount() {
        BankAccount bankAccount = (new BankAccountBuilder()).build();

        when(bankAccountRepository.findBankAccountById(null)).thenReturn(Optional.of(bankAccount));
        when(customerService.getCustomerById(null)).thenReturn(bankAccount.getCustomers().getFirst());
        when(bankAccountRepository.save(bankAccount)).thenReturn(bankAccount);

        BankAccount result = bankAccountService.addCustomerToBankAccount(null, null);

        verify(bankAccountRepository).save(Mockito.any());
        assertEquals(2, result.getCustomers().size());

    }

    @Test
    @Description("Test if Customer is removed from BankAccount")
    public void testRemoveCustomerFromBankAccount() {
        BankAccount bankAccount = (new BankAccountBuilder()).build();
        bankAccount.addCustomer(new Customer("test"));

        when(bankAccountRepository.findBankAccountById(null)).thenReturn(Optional.of(bankAccount));
        when(customerService.getCustomerById(null)).thenReturn(bankAccount.getCustomers().getFirst());
        when(bankAccountRepository.save(bankAccount)).thenReturn(bankAccount);

        BankAccount result = bankAccountService.removeCustomerFromBankAccount(null, null);

        assertEquals(1, result.getCustomers().size());
        verify(bankAccountRepository).save(Mockito.any());
    }

    @Test
    @Description("Test if Customer is not removed from BankAccount and throws exception")
    public void testRemoveCustomerFromBankAccountThrowsException() {
        BankAccount bankAccount = (new BankAccountBuilder()).build();

        when(bankAccountRepository.findBankAccountById(null)).thenReturn(Optional.of(bankAccount));
        when(customerService.getCustomerById(null)).thenReturn(bankAccount.getCustomers().getFirst());

        assertThrows(
                TooLittleCustomersInBankAccountException.class,
                () -> bankAccountService.removeCustomerFromBankAccount(null, null)
        );
        assertEquals(1, bankAccount.getCustomers().size());
        verify(bankAccountRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    @Description("Test if BankAccount is deleted by ID")
    public void testDeleteBankAccountById() {
        BankAccount bankAccount = (new BankAccountBuilder()).build();

        when(bankAccountRepository.findBankAccountById(null)).thenReturn(Optional.of(bankAccount));

        BankAccount result = bankAccountService.deleteBankAccountById(null);

        verify(bankAccountRepository).removeBankAccountById(null);
        assertNotNull(result);
    }

    @Test
    @Description("Test if BankAccount is deleted by ID returns null")
    public void testDeleteBankAccountByIdReturnsNull() {
        when(bankAccountRepository.findBankAccountById(null)).thenReturn(Optional.empty());

        BankAccount result = bankAccountService.deleteBankAccountById(null);

        assertNull(result);
    }
}