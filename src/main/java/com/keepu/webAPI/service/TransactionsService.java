package com.keepu.webAPI.service;

import com.keepu.webAPI.dto.request.CreateTransactionRequest;
import com.keepu.webAPI.dto.response.TransactionResponse;
import com.keepu.webAPI.exception.NotFoundException;
import com.keepu.webAPI.mapper.TransactionsMapper;
import com.keepu.webAPI.model.Transactions;
import com.keepu.webAPI.model.Wallet;
import com.keepu.webAPI.model.enums.TransactionType;
import com.keepu.webAPI.repository.TransactionsRepository;
import com.keepu.webAPI.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionsService {

    private final TransactionsRepository transactionsRepository;
    private final WalletRepository walletRepository;
    private final TransactionsMapper transactionsMapper;

    @Transactional
    public TransactionResponse createTransaction(CreateTransactionRequest request) {
        Wallet wallet = walletRepository.findById(request.walletId())
                .orElseThrow(() -> new NotFoundException("Billetera no encontrada"));

        Transactions transaction = transactionsMapper.toTransactionEntity(request, wallet);
        Transactions savedTransaction = transactionsRepository.save(transaction);

        return transactionsMapper.toTransactionResponse(savedTransaction);
    }

    @Transactional(readOnly = true)
    public TransactionResponse getTransactionById(Integer id) {
        Transactions transaction = transactionsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Transacción no encontrada"));
        return transactionsMapper.toTransactionResponse(transaction);
    }

    @Transactional(readOnly = true)
    public List<TransactionResponse> getTransactionsByWalletId(String walletId) {
        Wallet wallet = walletRepository.findByWalletId(walletId)
                .orElseThrow(() -> new NotFoundException("Billetera no encontrada"));

        List<Transactions> transactions = transactionsRepository.findByWallet(wallet);

        return transactions.stream()
                .map(transactionsMapper::toTransactionResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<TransactionResponse> getFilteredTransactionsByWalletId( String walletId, TransactionType type,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Double minAmount,
            Double maxAmount
    )
    {
        Wallet wallet = walletRepository.findByWalletId(walletId)
                .orElseThrow(() -> new NotFoundException("Billetera no encontrada"));

        List<Transactions> transactions = transactionsRepository.findFilteredTransactions(
                wallet, type, startDate, endDate, minAmount, maxAmount
        );

        return transactions.stream()
                .map(transactionsMapper::toTransactionResponse)
                .toList();
    }
}