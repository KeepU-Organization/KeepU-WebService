package com.keepu.webAPI.service;

import com.keepu.webAPI.dto.response.TransactionResponse;
import com.keepu.webAPI.exception.NotFoundException;
import com.keepu.webAPI.mapper.TransactionsMapper;
import com.keepu.webAPI.model.Transactions;
import com.keepu.webAPI.model.Wallet;
import com.keepu.webAPI.model.enums.TransactionType;
import com.keepu.webAPI.model.enums.WalletType;
import com.keepu.webAPI.repository.TransactionsRepository;
import com.keepu.webAPI.repository.WalletRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionsServiceUnitTest {

    @Mock
    private TransactionsRepository transactionsRepository;

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private TransactionsMapper transactionsMapper;

    @InjectMocks
    private TransactionsService transactionsService;

    // CP15 – Se encontraron transacciones de la wallet deseada
    @Test
    @DisplayName("CP15 – Se encontraron transacciones de la wallet deseada")
    void testGetTransactionsByWalletId_Successful() {
        String walletId = "W-29827C80";

        Wallet wallet = new Wallet();
        wallet.setWalletId(walletId);
        wallet.setWalletType(WalletType.PARENT); // Asumiendo este tipo

        Transactions tx = new Transactions();
        tx.setAmount(BigDecimal.valueOf(25.0));
        tx.setDescription("Transferencia realizada");
        tx.setTransactionType(TransactionType.DEPOSIT);
        tx.setTransactionDate(LocalDateTime.now());

        TransactionResponse response = new TransactionResponse(
                BigDecimal.valueOf(25.0),
                "Transferencia realizada",
                TransactionType.DEPOSIT,
                tx.getTransactionDate(),
                null,
                null,
                null
        );

        when(walletRepository.findByWalletId(walletId)).thenReturn(Optional.of(wallet));
        when(transactionsRepository.findByWallet(wallet)).thenReturn(List.of(tx));
        when(transactionsMapper.toResponse(tx)).thenReturn(response);

        List<TransactionResponse> result = transactionsService.getTransactionsByWalletId(walletId);

        assertEquals(1, result.size());
        assertEquals(BigDecimal.valueOf(25.0), result.get(0).amount());
        assertEquals("Transferencia realizada", result.get(0).description());
        assertEquals(TransactionType.DEPOSIT, result.get(0).transactionType());
    }

    // CP16 – No existen transacciones en la wallet deseada
    @Test
    @DisplayName("CP16 – No existen transacciones en la wallet deseada")
    void testGetTransactionsByWalletId_EmptyTransactions() {
        String walletId = "W-29827C10";

        Wallet wallet = new Wallet();
        wallet.setWalletId(walletId);
        wallet.setWalletType(WalletType.PARENT);

        when(walletRepository.findByWalletId(walletId)).thenReturn(Optional.of(wallet));
        when(transactionsRepository.findByWallet(wallet)).thenReturn(Collections.emptyList());

        List<TransactionResponse> result = transactionsService.getTransactionsByWalletId(walletId);

        assertTrue(result.isEmpty());
    }

    // CP17 – Wallet no encontrada (ID erróneo)
    @Test
    @DisplayName("CP17 – Wallet no encontrada (ID erróneo)")
    void testGetTransactionsByWalletId_WalletNotFound() {
        String walletId = "W-29827B10";

        when(walletRepository.findByWalletId(walletId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            transactionsService.getTransactionsByWalletId(walletId);
        });

        assertEquals("Billetera no encontrada", exception.getMessage());
    }
}
