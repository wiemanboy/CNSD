package com.wiemanboy.cnsdbankapplication.presentation.dto.response;

import com.wiemanboy.cnsdbankapplication.domain.BankAccount;
import com.wiemanboy.cnsdbankapplication.domain.enums.AccountStatus;

import java.util.List;
import java.util.UUID;

public record BankAccountDTO(
        UUID id,
        List<CustomerDTO> customers,
        AccountStatus status,
        int value
) {
    public static BankAccountDTO from(BankAccount bankAccount) {
        return new BankAccountDTO(
                bankAccount.getId(),
                CustomerDTO.from(bankAccount.getCustomers()),
                bankAccount.getStatus(),
                bankAccount.getValue()
        );
    }

    public static List<BankAccountDTO> from(List<BankAccount> bankAccounts) {
        return bankAccounts.stream()
                .map(BankAccountDTO::from)
                .toList();
    }
}
