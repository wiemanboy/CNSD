package com.wiemanboy.cnsdbankapplication.application;

import com.wiemanboy.cnsdbankapplication.data.BankAccountRepository;
import com.wiemanboy.cnsdbankapplication.domain.BankAccount;
import com.wiemanboy.cnsdbankapplication.domain.enums.AccountStatus;
import com.wiemanboy.cnsdbankapplication.domain.exception.BankAccountNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class BankAccountService {

    private final CustomerService customerService;
    private final BankAccountRepository bankAccountRepository;

    public BankAccountService(CustomerService customerService, BankAccountRepository bankAccountRepository) {
        this.customerService = customerService;
        this.bankAccountRepository = bankAccountRepository;
    }

    public BankAccount createBankAccountForCustomer(UUID customerId) {
        BankAccount bankAccount = new BankAccount(customerService.getCustomerById(customerId));

        bankAccountRepository.save(bankAccount);

        return bankAccount;
    }

    public BankAccount getBankAccountById(UUID bankAccountId) {
        return bankAccountRepository.findBankAccountById(bankAccountId).orElseThrow(
                () -> new BankAccountNotFoundException(bankAccountId)
        );
    }

    public List<BankAccount> getBankAccounts() {
        return bankAccountRepository.findAll();
    }

    public BankAccount updateBankAccountById(UUID bankAccountId, AccountStatus status) {
        BankAccount bankAccount = getBankAccountById(bankAccountId);

        bankAccount.setStatus(status);

        bankAccountRepository.save(bankAccount);
        return bankAccount;
    }

    public BankAccount addCustomerToBankAccount(UUID bankAccountId, UUID customerId) {
        BankAccount bankAccount = getBankAccountById(bankAccountId);

        bankAccount.addCustomer(customerService.getCustomerById(customerId));

        bankAccountRepository.save(bankAccount);
        return bankAccount;
    }

    public BankAccount removeCustomerFromBankAccount(UUID bankAccountId, UUID customerId) {
        BankAccount bankAccount = getBankAccountById(bankAccountId);

        bankAccount.removeCustomer(customerService.getCustomerById(customerId));

        bankAccountRepository.save(bankAccount);
        return bankAccount;
    }

    public BankAccount removeBankAccountById(UUID bankAccountId) {
        return bankAccountRepository.removeBankAccountById(bankAccountId).orElse(null);
    }
}
