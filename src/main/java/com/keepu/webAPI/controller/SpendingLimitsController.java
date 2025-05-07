package com.keepu.webAPI.controller;

import com.keepu.webAPI.dto.request.CreateSpendingLimitRequest;
import com.keepu.webAPI.dto.response.SpendingLimitResponse;
import com.keepu.webAPI.service.SpendingLimitsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/spending-limits")
@RequiredArgsConstructor
public class SpendingLimitsController {

    private final SpendingLimitsService spendingLimitsService;

    @PostMapping
    public ResponseEntity<SpendingLimitResponse> create(@Valid @RequestBody CreateSpendingLimitRequest request) {
        return ResponseEntity.ok(spendingLimitsService.createSpendingLimit(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpendingLimitResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(spendingLimitsService.getSpendingLimitById(id));
    }
}