package com.keepu.webAPI.dto.request;

import com.keepu.webAPI.model.GiftCards;
import com.keepu.webAPI.model.Stores;
import com.keepu.webAPI.model.Wallet;
import com.keepu.webAPI.model.enums.TransactionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CreateTransactionRequest(

        @NotNull(message = "La billetera no puede ser nula")
        Wallet wallet,

        @NotNull(message = "El monto no puede ser nulo")
        BigDecimal amount,

        @NotBlank(message = "La descripción no puede estar vacía")
        String description,

        @NotBlank(message = "El tipo de transacción no puede estar vacío")
        TransactionType transactionType,

        GiftCards giftCard,
        Stores store
) {}