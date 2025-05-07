package com.keepu.webAPI.controller;

import com.keepu.webAPI.dto.request.CreateWalletRequest;
import com.keepu.webAPI.dto.response.WalletResponse;
import com.keepu.webAPI.service.WalletService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/wallets")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @PostMapping
    public ResponseEntity<WalletResponse> create(@Valid @RequestBody CreateWalletRequest request) {
        return ResponseEntity.ok(walletService.createWallet(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<WalletResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(walletService.getWalletById(id));
    }
}