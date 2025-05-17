package com.keepu.webAPI.dto.response;

import java.math.BigDecimal;

public record SpendingLimitResponse(
        Integer id,
        BigDecimal maxAmount,
        String walletId
) {}