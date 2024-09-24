package com.wiemanboy.cnsdbankapplication.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CustomerUpdateDTO(
        @NotNull
        @NotBlank
        String name
) {
}
