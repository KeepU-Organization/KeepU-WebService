package com.keepu.webAPI.dto.response;

import com.keepu.webAPI.enums.WalletType;

public record WalletResponse(
        Integer id,
        WalletType walletType,
        Double balance,
        Integer userId
) {}