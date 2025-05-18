package com.keepu.webAPI.mapper;

import com.keepu.webAPI.dto.request.CreateSpendingLimitRequest;
import com.keepu.webAPI.dto.response.SpendingLimitResponse;
import com.keepu.webAPI.model.SpendingLimits;
import com.keepu.webAPI.model.Wallet;
import org.springframework.stereotype.Component;

@Component
public class SpendingLimitsMapper {

    public SpendingLimitResponse toSpendingLimitResponse(SpendingLimits spendingLimit) {
        if (spendingLimit == null) {
            return null;
        }
        return new SpendingLimitResponse(
                spendingLimit.getId(),
                spendingLimit.getMaxAmount(),
                spendingLimit.getWallet().getWalletId() // ← Arreglado aquí
        );
    }

    public SpendingLimits toSpendingLimitEntity(CreateSpendingLimitRequest request, Wallet wallet) {
        if (request == null || wallet == null) {
            return null;
        }

        SpendingLimits spendingLimit = new SpendingLimits();
        spendingLimit.setMaxAmount(request.maxAmount());
        spendingLimit.setWallet(wallet);
        return spendingLimit;
    }
}