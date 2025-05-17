package com.keepu.webAPI.dto.response;

import java.math.BigDecimal;

public record TransferResponse(
        WalletResponse senderWallet,
        WalletResponse receiverWallet,
        BigDecimal transactionAmount
) {
}
