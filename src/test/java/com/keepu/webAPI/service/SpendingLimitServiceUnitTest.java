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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class SpendingLimitServiceUnitTest {

    @Mock
    private SpendingLimitsRepository spendingLimitsRepository;

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private SpendingLimitsMapper spendingLimitsMapper;

    @InjectMocks
    private SpendingLimitsService spendingLimitsService;

    @BeforeEach
    void setUp() {
        spendingLimitsRepository = mock(SpendingLimitsRepository.class);
        walletRepository = mock(WalletRepository.class);
        spendingLimitsMapper = mock(SpendingLimitsMapper.class);
        spendingLimitsService = new SpendingLimitsService(
                spendingLimitsRepository,
                walletRepository,
                spendingLimitsMapper
        );
    }

    @Test
    @DisplayName("CP18 – Se establece el límite de gasto con éxito")
    void testCreateOrUpdateSpendingLimit_Successful() {
        String walletId = "W-960C0562";
        BigDecimal maxAmount = BigDecimal.valueOf(50.00);

        CreateSpendingLimitRequest request = new CreateSpendingLimitRequest(maxAmount, walletId);

        Wallet wallet = new Wallet();
        wallet.setWalletId(walletId);
        wallet.setWalletType(WalletType.STANDARD);

        SpendingLimits newLimit = new SpendingLimits();
        newLimit.setId(1);
        newLimit.setWallet(wallet);
        newLimit.setMaxAmount(maxAmount);

        SpendingLimitResponse expectedResponse = new SpendingLimitResponse(1, maxAmount, walletId);

        when(walletRepository.findByWalletId(walletId)).thenReturn(Optional.of(wallet));
        when(spendingLimitsRepository.findByWallet(wallet)).thenReturn(Optional.empty());

        when(spendingLimitsMapper.toSpendingLimitEntity(any(CreateSpendingLimitRequest.class), any(Wallet.class)))
                .thenReturn(newLimit);

        when(spendingLimitsRepository.save(any(SpendingLimits.class))).thenReturn(newLimit);
        when(spendingLimitsMapper.toSpendingLimitResponse(any(SpendingLimits.class))).thenReturn(expectedResponse);

        SpendingLimitResponse response = spendingLimitsService.createOrUpdateSpendingLimit(request);

        assertEquals(expectedResponse, response);
    }

    @Test
    @DisplayName("CP19 – La wallet a la que quiere acceder es errónea")
    void testCreateOrUpdateSpendingLimit_WalletNotFound() {
        String walletId = "W-960C0561";
        BigDecimal maxAmount = BigDecimal.valueOf(50.00);

        CreateSpendingLimitRequest request = new CreateSpendingLimitRequest(maxAmount, walletId);

        when(walletRepository.findByWalletId(walletId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> spendingLimitsService.createOrUpdateSpendingLimit(request));
    }
}