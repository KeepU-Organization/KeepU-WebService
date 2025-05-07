package com.keepu.webAPI.mapper;

import com.keepu.webAPI.dto.request.CreateTransactionRequest;
import com.keepu.webAPI.dto.response.TransactionResponse;
import com.keepu.webAPI.model.Transactions;
import com.keepu.webAPI.model.Wallet;
import org.springframework.stereotype.Component;

@Component
public class TransactionsMapper {

    public TransactionResponse toTransactionResponse(Transactions transaction) {
        if (transaction == null) {
            return null;
        }
        return new TransactionResponse(
                transaction.getId(),
                transaction.getAmount(),
                transaction.getDescription(),
                transaction.getTransactionDate(),
                transaction.getWallet().getId()
        );
    }

    public Transactions toTransactionEntity(CreateTransactionRequest request, Wallet wallet) {
        if (request == null || wallet == null) {
            return null;
        }

        Transactions transaction = new Transactions();
        transaction.setAmount(request.amount());
        transaction.setDescription(request.description());
        transaction.setTransactionDate(request.transactionDate());
        transaction.setWallet(wallet);
        return transaction;
    }
}