package com.keepu.webAPI.service;

import com.keepu.webAPI.dto.request.CreateTransactionRequest;
import com.keepu.webAPI.model.GiftCards;
import com.keepu.webAPI.model.Stores;
import com.keepu.webAPI.model.Transactions;
import com.keepu.webAPI.model.Wallet;
import com.keepu.webAPI.model.enums.TransactionType;
import com.keepu.webAPI.repository.StoresRepository;
import com.keepu.webAPI.repository.TransactionsRepository;
import com.keepu.webAPI.repository.WalletRepository;
import com.keepu.webAPI.exception.NotFoundException;
import com.keepu.webAPI.dto.response.TransactionResponse;
import com.keepu.webAPI.mapper.TransactionsMapper;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.Store;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionsService {

    private final TransactionsRepository transactionsRepository;
    private final WalletRepository walletRepository;
    private final TransactionsMapper transactionsMapper;

    public List<TransactionResponse> getTransactionsByWalletId(String walletId) {
        Wallet wallet = walletRepository.findByWalletId(walletId)
                .orElseThrow(() -> new NotFoundException("Billetera no encontrada"));
        List<Transactions> transactions = transactionsRepository.findByWallet(wallet);
        return transactions.stream()
                .map(transactionsMapper::toResponse)
                .toList();
    }

    public void recordTransfer(CreateTransactionRequest request) {
        Transactions transaction = new Transactions();

        transaction.setWallet(request.wallet());
        transaction.setAmount(request.amount());
        transaction.setDescription(request.description());
        transaction.setTransactionType(request.transactionType());
        transaction.setTransactionDate(LocalDateTime.now());

        transaction.setStore(request.store());
        transaction.setGiftCard(request.giftCard());


        transactionsRepository.save(transaction);
    }
}