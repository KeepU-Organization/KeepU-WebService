package com.keepu.webAPI.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateTransferRequest(
        @NotNull(message="El ID de la billetera del remitente no puede ser nulo")
        String senderWalletId,
        @NotNull(message="El ID de la billetera del receptor no puede ser nulo")
        String receiverWalletId,
        //no nulo ni menor de 0

        @Min(value=0, message = "El monto de la transacción no puede ser menor a 0")
        @NotNull (message="El monto de la transacción no puede ser nulo")
        BigDecimal transactionAmount
) {
}
