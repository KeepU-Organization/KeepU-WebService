package com.keepu.webAPI.dto.response;

import com.keepu.webAPI.model.enums.WalletType;

public record WalletResponse(
        Integer id,
        String walletId,
        WalletType walletType,
        java.math.BigDecimal balance,
        Long userId
) {}