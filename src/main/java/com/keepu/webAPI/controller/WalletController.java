package com.keepu.webAPI.controller;

import com.keepu.webAPI.model.Wallet;
import com.keepu.webAPI.service.WalletService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/wallets")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping
    public List<Wallet> getAll() {
        return walletService.findAll();
    }

    @GetMapping("/{id}")
    public Wallet getById(@PathVariable Integer id) {
        return walletService.findById(id);
    }

    @PostMapping
    public Wallet save(@RequestBody Wallet wallet) {
        return walletService.save(wallet);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        walletService.deleteById(id);
    }
}
