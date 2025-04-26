package com.keepu.webAPI.controller;

import com.keepu.webAPI.model.WalletTypes;
import com.keepu.webAPI.service.WalletTypesService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/wallet-types")
public class WalletTypesController {

    private final WalletTypesService service;

    public WalletTypesController(WalletTypesService service) {
        this.service = service;
    }

    @GetMapping
    public List<WalletTypes> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public WalletTypes getById(@PathVariable Integer id) {
        return service.findById(id);
    }

    @PostMapping
    public WalletTypes save(@RequestBody WalletTypes walletType) {
        return service.save(walletType);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.deleteById(id);
    }
}
