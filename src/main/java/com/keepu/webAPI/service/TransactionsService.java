package com.keepu.webAPI.service;

import com.keepu.webAPI.model.Transactions;
import com.keepu.webAPI.model.Wallet;
import com.keepu.webAPI.model.enums.TransactionType;
import com.keepu.webAPI.repository.TransactionsRepository;
import com.keepu.webAPI.repository.WalletRepository;
import com.keepu.webAPI.exception.NotFoundException;
import com.keepu.webAPI.dto.response.TransactionResponse;
import com.keepu.webAPI.mapper.TransactionsMapper;
import lombok.RequiredArgsConstructor;
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


    public void recordTransfer(Wallet senderWallet, double amount, String description) {
        Transactions transaction = new Transactions();
        transaction.setWallet(senderWallet);
        transaction.setAmount(amount);
        transaction.setDescription(description);
        transaction.setTransactionType(TransactionType.TRANSFER);
        transaction.setTransactionDate(LocalDateTime.now());
        transactionsRepository.save(transaction);
    }
}