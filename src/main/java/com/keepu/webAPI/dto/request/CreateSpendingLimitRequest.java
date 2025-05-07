package com.keepu.webAPI.dto.request;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record CreateSpendingLimitRequest(
        @NotNull(message = "El monto máximo no puede ser nulo")
        BigDecimal maxAmount,

        @NotNull(message = "El ID de la billetera no puede ser nulo")
        Integer walletId
) {}