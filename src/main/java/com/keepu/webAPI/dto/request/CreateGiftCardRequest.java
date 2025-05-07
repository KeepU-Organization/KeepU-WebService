package com.keepu.webAPI.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record CreateGiftCardRequest(
        @NotBlank(message = "El código no puede estar vacío")
        String code,

        @NotNull(message = "El monto no puede ser nulo")
        BigDecimal amount,

        @NotNull(message = "El ID de la tienda no puede ser nulo")
        Integer storeId
) {}