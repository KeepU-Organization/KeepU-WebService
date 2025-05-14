package com.keepu.webAPI.controller;

import com.keepu.webAPI.dto.request.CreateTransactionRequest;
import com.keepu.webAPI.dto.response.TransactionResponse;
import com.keepu.webAPI.model.enums.TransactionType;
import com.keepu.webAPI.service.TransactionsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionsController {

    private final TransactionsService transactionsService;

    @PostMapping
    public ResponseEntity<TransactionResponse> create(@Valid @RequestBody CreateTransactionRequest request) {
        return ResponseEntity.ok(transactionsService.createTransaction(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(transactionsService.getTransactionById(id));
    }

    @GetMapping("/wallet/{walletId}")
    public List<TransactionResponse> getTransactionsByWalletId(@PathVariable String walletId) {
        return transactionsService.getTransactionsByWalletId(walletId);
    }

    @GetMapping("/wallet/{walletId}/filter")
    public List<TransactionResponse> getFilteredTransactions(
            @PathVariable String walletId,
            @RequestParam(required = false) TransactionType type,
            @RequestParam(required = false) LocalDateTime startDate,
            @RequestParam(required = false) LocalDateTime endDate,
            @RequestParam(required = false) Double minAmount,
            @RequestParam(required = false) Double maxAmount
    ) {
        return transactionsService.getFilteredTransactionsByWalletId(walletId, type, startDate, endDate, minAmount, maxAmount);
    }

}