package com.keepu.webAPI.dto.response;

import com.keepu.webAPI.model.enums.StoreType;
import com.keepu.webAPI.model.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponse(
        BigDecimal amount,
        String description,
        TransactionType transactionType,
        LocalDateTime transactionDate,
        String giftCardCode,
        String storeName,
        StoreType storeType

) {}