package com.wiemanboy.cnsdbankapplication.presentation.dto.request;

import com.wiemanboy.cnsdbankapplication.domain.enums.AccountStatus;
import jakarta.validation.constraints.NotNull;

public record BankAccountUpdateDTO(
        @NotNull
        AccountStatus status
) {

}
