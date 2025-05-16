package com.keepu.webAPI.dto.request;

import com.keepu.webAPI.model.enums.WalletType;
import jakarta.validation.constraints.NotNull;

public record CreateWalletRequest(
        @NotNull(message = "El tipo de billetera no puede ser nulo")
        WalletType walletType,

        @NotNull(message = "El ID del usuario no puede ser nulo")
        Integer userId
) {}