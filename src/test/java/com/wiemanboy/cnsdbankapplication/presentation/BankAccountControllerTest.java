package com.wiemanboy.cnsdbankapplication.presentation;

import com.wiemanboy.cnsdbankapplication.application.BankAccountService;
import com.wiemanboy.cnsdbankapplication.domain.BankAccount;
import com.wiemanboy.cnsdbankapplication.domain.BankAccountBuilder;
import com.wiemanboy.cnsdbankapplication.domain.enums.AccountStatus;
import com.wiemanboy.cnsdbankapplication.presentation.dto.request.BankAccountCreateDTO;
import com.wiemanboy.cnsdbankapplication.presentation.dto.request.BankAccountUpdateCustomerDTO;
import com.wiemanboy.cnsdbankapplication.presentation.dto.request.BankAccountUpdateDTO;
import com.wiemanboy.cnsdbankapplication.presentation.dto.response.BankAccountDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Description;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BankAccountControllerTest {
    private BankAccountService bankAccountService;
    private BankAccountController bankAccountController;

    @BeforeEach
    void setUp() {
        bankAccountService = mock(BankAccountService.class);
        bankAccountController = new BankAccountController(bankAccountService);
    }

    @Test
    @Description("Test create bank account returns a BankAccountDTO")
    void testCreateBankAccount() {
        BankAccount bankAccount = (new BankAccountBuilder()).build();

        when(bankAccountService.createBankAccountForCustomer(null)).thenReturn(bankAccount);

        BankAccountDTO result = bankAccountController.createBankAccount(new BankAccountCreateDTO(null));

        assertInstanceOf(BankAccountDTO.class, result);
        assertEquals(bankAccount.getBalance(), result.value());
    }

    @Test
    @Description("Test get bank account by id returns a BankAccountDTO")
    void testGetBankAccount() {
        BankAccount bankAccount = (new BankAccountBuilder()).build();

        when(bankAccountService.getBankAccountById(null)).thenReturn(bankAccount);

        BankAccountDTO result = bankAccountController.getBankAccount(null);

        assertInstanceOf(BankAccountDTO.class, result);
        assertEquals(bankAccount.getId(), result.id());
    }

    @Test
    @Description("Test get bank accounts returns a list of BankAccountDTO")
    void testGetBankAccounts() {
        List<BankAccount> bankAccounts = List.of(
                (new BankAccountBuilder()).build(),
                (new BankAccountBuilder()).build()
        );

        when(bankAccountService.getBankAccounts()).thenReturn(bankAccounts);

        List<BankAccountDTO> result = bankAccountController.getBankAccounts();

        assertEquals(bankAccounts.size(), result.size());
    }

    @Test
    @Description("Test update bank account by id returns a BankAccountDTO")
    void testUpdateBankAccount() {
        BankAccount bankAccount = (new BankAccountBuilder()).setStatus(AccountStatus.SUSPENDED).build();

        when(bankAccountService.updateBankAccountById(null, AccountStatus.SUSPENDED)).thenReturn(bankAccount);

        BankAccountDTO result = bankAccountController.updateBankAccount(null, new BankAccountUpdateDTO(AccountStatus.SUSPENDED));

        assertInstanceOf(BankAccountDTO.class, result);
        assertEquals(bankAccount.getStatus(), result.status());
    }

    @Test
    @Description("Test add customer to bank account returns a BankAccountDTO")
    void testAddCustomerToBankAccount() {
        BankAccount bankAccount = (new BankAccountBuilder()).build();

        when(bankAccountService.addCustomerToBankAccount(null, null)).thenReturn(bankAccount);

        BankAccountDTO result = bankAccountController.addCustomerToBankAccount(null, new BankAccountUpdateCustomerDTO(null));

        assertInstanceOf(BankAccountDTO.class, result);
        assertEquals(bankAccount.getId(), result.id());
    }

    @Test
    @Description("Test remove customer from bank account returns a BankAccountDTO")
    void testRemoveCustomerFromBankAccount() {
        BankAccount bankAccount = (new BankAccountBuilder()).build();

        when(bankAccountService.removeCustomerFromBankAccount(null, null)).thenReturn(bankAccount);

        BankAccountDTO result = bankAccountController.removeCustomerFromBankAccount(null, new BankAccountUpdateCustomerDTO(null));

        assertInstanceOf(BankAccountDTO.class, result);
        assertEquals(bankAccount.getId(), result.id());
    }

    @Test
    @Description("Test delete bank account by id returns a BankAccountDTO")
    void testDeleteBankAccount() {
        BankAccount bankAccount = (new BankAccountBuilder()).build();

        when(bankAccountService.deleteBankAccountById(null)).thenReturn(bankAccount);

        BankAccountDTO result = bankAccountController.deleteBankAccount(null);

        assertInstanceOf(BankAccountDTO.class, result);
        assertEquals(bankAccount.getId(), result.id());
    }
}