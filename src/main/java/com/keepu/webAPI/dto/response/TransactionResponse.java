package com.keepu.webAPI.dto.response;

import java.time.LocalDateTime;

public record TransactionResponse(
        Integer id,
        Double amount,
        String description,
        LocalDateTime transactionDate,
        Integer walletId
) {}