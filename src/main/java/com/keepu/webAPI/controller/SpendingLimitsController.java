package com.keepu.webAPI.controller;

import com.keepu.webAPI.dto.request.CreateSpendingLimitRequest;
import com.keepu.webAPI.dto.response.SpendingLimitResponse;
import com.keepu.webAPI.service.SpendingLimitsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/wallets/{walletId}/spending-limit")
@RequiredArgsConstructor
public class SpendingLimitsController {

    private final SpendingLimitsService spendingLimitsService;

    // Crea o actualiza el límite de gasto de una billetera
    @PostMapping
    public ResponseEntity<SpendingLimitResponse> createOrUpdate(
            @PathVariable String walletId,
            @Valid @RequestBody CreateSpendingLimitRequest request) {

        // Validación de coherencia entre el path variable y el body
        if (!walletId.equals(request.walletId())) {
            return ResponseEntity.badRequest().build();
        }

        SpendingLimitResponse response = spendingLimitsService.createOrUpdateSpendingLimit(request);
        return ResponseEntity.ok(response);
    }

    // Obtiene el límite de gasto asociado a una billetera
    @GetMapping
    public ResponseEntity<SpendingLimitResponse> getByWalletId(
            @PathVariable String walletId) {

        SpendingLimitResponse response = spendingLimitsService.getSpendingLimitByWalletId(walletId);
        return ResponseEntity.ok(response);
    }
}