package com.keepu.webAPI.dto.request;

import java.math.BigDecimal;

public record CreateShopRequest(
        String walletId,
        Integer storeId,
        Integer quantity,
        BigDecimal totalPrice
) {
}
