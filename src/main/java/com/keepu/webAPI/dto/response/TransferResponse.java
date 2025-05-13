package com.keepu.webAPI.dto.response;

import com.keepu.webAPI.model.Wallet;

import java.math.BigDecimal;

public record TransferResponse(
        WalletResponse senderWallet,
WalletResponse receiverWallet,
        BigDecimal transactionAmount
) {
}
