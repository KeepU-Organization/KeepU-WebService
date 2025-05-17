package com.keepu.webAPI.service;

import com.keepu.webAPI.dto.request.CreateSpendingLimitRequest;
import com.keepu.webAPI.dto.response.SpendingLimitResponse;
import com.keepu.webAPI.exception.NotFoundException;
import com.keepu.webAPI.mapper.SpendingLimitsMapper;
import com.keepu.webAPI.model.SpendingLimits;
import com.keepu.webAPI.model.Wallet;
import com.keepu.webAPI.model.enums.WalletType;
import com.keepu.webAPI.repository.SpendingLimitsRepository;
import com.keepu.webAPI.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class SpendingLimitsService {

    private final SpendingLimitsRepository spendingLimitsRepository;
    private final WalletRepository walletRepository;
    private final SpendingLimitsMapper spendingLimitsMapper;

    @Transactional
    public SpendingLimitResponse createOrUpdateSpendingLimit(CreateSpendingLimitRequest request) {
        if (request.maxAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto máximo debe ser mayor a cero");
        }

        Wallet wallet = walletRepository.findByWalletId(request.walletId())
                .orElseThrow(() -> new NotFoundException("Billetera no encontrada"));

        if (wallet.getWalletType() == WalletType.PARENT) {
            throw new IllegalArgumentException("No se puede aplicar un límite de gasto a una billetera de tipo PARENT");
        }

        SpendingLimits existing = spendingLimitsRepository.findByWallet(wallet).orElse(null);

        if (existing != null) {
            existing.setMaxAmount(request.maxAmount());
            SpendingLimits updated = spendingLimitsRepository.save(existing);
            return spendingLimitsMapper.toSpendingLimitResponse(updated);
        }

        SpendingLimits newLimit = spendingLimitsMapper.toSpendingLimitEntity(request, wallet);
        SpendingLimits saved = spendingLimitsRepository.save(newLimit);
        return spendingLimitsMapper.toSpendingLimitResponse(saved);
    }

    @Transactional(readOnly = true)
    public SpendingLimitResponse getSpendingLimitByWalletId(String walletId) {
        Wallet wallet = walletRepository.findByWalletId(walletId)
                .orElseThrow(() -> new NotFoundException("Billetera no encontrada"));

        SpendingLimits spendingLimit = spendingLimitsRepository.findByWallet(wallet)
                .orElseThrow(() -> new NotFoundException("Límite de gasto no encontrado para esta billetera"));

        return spendingLimitsMapper.toSpendingLimitResponse(spendingLimit);
    }
}
