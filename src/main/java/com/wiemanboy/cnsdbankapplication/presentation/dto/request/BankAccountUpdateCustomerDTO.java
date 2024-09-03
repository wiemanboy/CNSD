package com.wiemanboy.cnsdbankapplication.presentation.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record BankAccountUpdateCustomerDTO(
        @NotNull
        UUID customerId
) {
}
