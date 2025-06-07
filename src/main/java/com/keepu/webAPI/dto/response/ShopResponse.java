package com.keepu.webAPI.dto.response;

import com.keepu.webAPI.model.GiftCards;

import java.math.BigDecimal;
import java.util.List;

public record ShopResponse(
        WalletResponse wallet,
        List<GiftCards> giftCards,
        String storeName,
        BigDecimal amount
){}
