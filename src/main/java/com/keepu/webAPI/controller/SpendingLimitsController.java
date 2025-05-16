package com.keepu.webAPI.controller;

import com.keepu.webAPI.dto.request.CreateSpendingLimitRequest;
import com.keepu.webAPI.dto.response.SpendingLimitResponse;
import com.keepu.webAPI.service.SpendingLimitsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/wallets/spending-limit")
@RequiredArgsConstructor
public class SpendingLimitsController {

    private final SpendingLimitsService spendingLimitsService;

    // Crea o actualiza el límite de gasto de una billetera, walletId viene solo en el body
    @PostMapping
    public ResponseEntity<SpendingLimitResponse> createOrUpdate(
            @Valid @RequestBody CreateSpendingLimitRequest request) {

        SpendingLimitResponse response = spendingLimitsService.createOrUpdateSpendingLimit(request);
        return ResponseEntity.ok(response);
    }

    // Si quieres obtener el límite por walletId que venga en query param (GET normalmente no tiene body)
    @GetMapping
    public ResponseEntity<SpendingLimitResponse> getByWalletId(
            @RequestParam String walletId) {

        SpendingLimitResponse response = spendingLimitsService.getSpendingLimitByWalletId(walletId);
        return ResponseEntity.ok(response);
    }
}