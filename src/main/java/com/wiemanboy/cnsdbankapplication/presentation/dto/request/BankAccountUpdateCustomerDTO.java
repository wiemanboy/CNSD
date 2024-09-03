package com.wiemanboy.cnsdbankapplication.presentation.dto.request;

import java.util.UUID;

public record BankAccountUpdateCustomerDTO(
        UUID customerId
) {
}
