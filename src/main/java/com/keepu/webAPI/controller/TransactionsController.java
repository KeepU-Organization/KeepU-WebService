package com.keepu.webAPI.controller;

import com.keepu.webAPI.dto.response.TransactionResponse;
import com.keepu.webAPI.mapper.TransactionsMapper;
import com.keepu.webAPI.service.TransactionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionsController {

    private final TransactionsService transactionsService;

    @GetMapping("/wallet/{walletId}")
    public List<TransactionResponse> getTransactionsByWalletId(@PathVariable String walletId) {
        return transactionsService.getTransactionsByWalletId(walletId);
    }
}