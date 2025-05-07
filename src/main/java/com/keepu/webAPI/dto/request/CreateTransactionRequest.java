package com.keepu.webAPI.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record CreateTransactionRequest(
        @NotNull(message = "El monto no puede ser nulo")
        Double amount,

        @NotBlank(message = "La descripción no puede estar vacía")
        String description,

        @NotNull(message = "La fecha de la transacción no puede ser nula")
        LocalDateTime transactionDate,

        @NotNull(message = "El ID de la billetera no puede ser nulo")
        Integer walletId
) {}