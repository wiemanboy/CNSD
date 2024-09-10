package com.wiemanboy.cnsdbankapplication.presentation;

import com.wiemanboy.cnsdbankapplication.application.BankAccountService;
import com.wiemanboy.cnsdbankapplication.domain.BankAccount;
import com.wiemanboy.cnsdbankapplication.presentation.dto.request.BankAccountCreateDTO;
import com.wiemanboy.cnsdbankapplication.presentation.dto.request.BankAccountUpdateCustomerDTO;
import com.wiemanboy.cnsdbankapplication.presentation.dto.request.BankAccountUpdateDTO;
import com.wiemanboy.cnsdbankapplication.presentation.dto.response.BankAccountDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/bank-accounts")
public class BankAccountController {

    private final BankAccountService bankAccountService;

    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BankAccountDTO createBankAccount(@RequestBody BankAccountCreateDTO bankAccountCreateDTO) {
        return BankAccountDTO.from(bankAccountService.createBankAccountForCustomer(bankAccountCreateDTO.customerId()));
    }

    @GetMapping("/{id}")
    public BankAccountDTO getBankAccount(@PathVariable UUID id) {
        return BankAccountDTO.from(bankAccountService.getBankAccountById(id));
    }

    @GetMapping
    public List<BankAccountDTO> getBankAccounts() {
        return BankAccountDTO.from(bankAccountService.getBankAccounts());
    }

    @PutMapping("/{id}")
    public BankAccountDTO updateBankAccount(@PathVariable UUID id, @RequestBody BankAccountUpdateDTO bankAccountUpdateDTO) {
        return BankAccountDTO.from(bankAccountService.updateBankAccountById(id, bankAccountUpdateDTO.status()));
    }

    @PatchMapping("/{id}/add-customer")
    public BankAccountDTO addCustomerToBankAccount(@PathVariable UUID id, @RequestBody BankAccountUpdateCustomerDTO bankAccountUpdateCustomerDTO) {
        return BankAccountDTO.from(bankAccountService.addCustomerToBankAccount(id, bankAccountUpdateCustomerDTO.customerId()));
    }

    @PatchMapping("/{id}/remove-customer")
    public BankAccountDTO removeCustomerFromBankAccount(@PathVariable UUID id, @RequestBody BankAccountUpdateCustomerDTO bankAccountUpdateCustomerDTO) {
        return BankAccountDTO.from(bankAccountService.removeCustomerFromBankAccount(id, bankAccountUpdateCustomerDTO.customerId()));
    }

    @DeleteMapping("/{id}")
    public BankAccountDTO deleteBankAccount(@PathVariable UUID id) {
        BankAccount bankAccount = bankAccountService.deleteBankAccountById(id);
        if (bankAccount == null) {
            return null;
        }
        return BankAccountDTO.from(bankAccount);
    }
}
