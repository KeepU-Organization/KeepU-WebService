package com.keepu.webAPI.mapper;

import com.keepu.webAPI.dto.response.TransactionResponse;
import com.keepu.webAPI.model.Stores;
import com.keepu.webAPI.model.Transactions;
import com.keepu.webAPI.model.enums.StoreType;
import org.springframework.stereotype.Component;

import com.keepu.webAPI.dto.response.TransactionResponse;
import com.keepu.webAPI.model.Transactions;
import org.springframework.stereotype.Component;


import java.util.List;

@Component
public class TransactionsMapper {

    public TransactionResponse toResponse(Transactions transaction) {

        String giftCardCode = null;
        String storeName = null;
        StoreType storeType = null;
        if (transaction.getGiftCard() != null) {
            giftCardCode = transaction.getGiftCard().getCode();
        }

        if (transaction.getStore() != null) {
            storeName = transaction.getStore().getName();
            storeType = transaction.getStore().getType();
        }
        return new TransactionResponse(
                transaction.getAmount(),
                transaction.getDescription(),
                transaction.getTransactionType(),
                transaction.getTransactionDate(),
                giftCardCode,
                storeName,
                storeType
        );
    }





    public List<TransactionResponse> toTransactionResponseList(List<Transactions> list) {
        return list.stream().map(this::toResponse).toList();
    }
}