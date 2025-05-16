package com.keepu.webAPI.mapper;

import com.keepu.webAPI.dto.response.TransactionResponse;
import com.keepu.webAPI.model.Transactions;
import org.springframework.stereotype.Component;

import com.keepu.webAPI.dto.response.TransactionResponse;
import com.keepu.webAPI.model.Transactions;
import org.springframework.stereotype.Component;


import java.util.List;

@Component
public class TransactionsMapper {

    public TransactionResponse toResponse(Transactions transaction) {
        return new TransactionResponse(
                transaction.getAmount(),
                transaction.getDescription(),
                transaction.getTransactionType(),
                transaction.getTransactionDate()
        );
    }



    public TransactionResponse toTransactionResponse(Transactions t) {
        return new TransactionResponse(
                t.getAmount(),
                t.getDescription(),
                t.getTransactionType(),
                t.getTransactionDate()
        );
    }



    public List<TransactionResponse> toTransactionResponseList(List<Transactions> list) {
        return list.stream().map(this::toTransactionResponse).toList();
    }
}