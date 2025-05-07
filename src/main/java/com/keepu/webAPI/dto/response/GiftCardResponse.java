package com.keepu.webAPI.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record GiftCardResponse(
        Integer id,
        String code,
        BigDecimal amount,
        boolean isRedeemed,
        LocalDateTime createdAt,
        LocalDateTime redeemedAt,
        Integer storeId
) {}