package com.wiemanboy.cnsdbankapplication.presentation.dto.request;

import com.wiemanboy.cnsdbankapplication.domain.enums.AccountStatus;

public record BankAccountUpdateDTO(
        AccountStatus status
) {

}
