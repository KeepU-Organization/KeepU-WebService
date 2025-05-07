package com.keepu.webAPI.service;

import com.keepu.webAPI.dto.request.CreateSpendingLimitRequest;
import com.keepu.webAPI.dto.response.SpendingLimitResponse;
import com.keepu.webAPI.exception.NotFoundException;
import com.keepu.webAPI.mapper.SpendingLimitsMapper;
import com.keepu.webAPI.model.SpendingLimits;
import com.keepu.webAPI.model.Wallet;
import com.keepu.webAPI.repository.SpendingLimitsRepository;
import com.keepu.webAPI.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SpendingLimitsService {

    private final SpendingLimitsRepository spendingLimitsRepository;
    private final WalletRepository walletRepository;
    private final SpendingLimitsMapper spendingLimitsMapper;

    @Transactional
    public SpendingLimitResponse createSpendingLimit(CreateSpendingLimitRequest request) {
        Wallet wallet = walletRepository.findById(request.walletId())
                .orElseThrow(() -> new NotFoundException("Billetera no encontrada"));

        SpendingLimits spendingLimit = spendingLimitsMapper.toSpendingLimitEntity(request, wallet);
        SpendingLimits savedSpendingLimit = spendingLimitsRepository.save(spendingLimit);

        return spendingLimitsMapper.toSpendingLimitResponse(savedSpendingLimit);
    }

    @Transactional(readOnly = true)
    public SpendingLimitResponse getSpendingLimitById(Integer id) {
        SpendingLimits spendingLimit = spendingLimitsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Límite de gasto no encontrado"));
        return spendingLimitsMapper.toSpendingLimitResponse(spendingLimit);
    }
}