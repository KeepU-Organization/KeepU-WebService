package com.keepu.webAPI.controller;

import com.keepu.webAPI.dto.request.CreateWalletRequest;
import com.keepu.webAPI.dto.request.CreateTransferRequest;
import com.keepu.webAPI.dto.response.TransferResponse;
import com.keepu.webAPI.dto.response.WalletResponse;
import com.keepu.webAPI.model.Wallet;
import com.keepu.webAPI.service.WalletService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/wallets")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @PostMapping
    public ResponseEntity<WalletResponse> create(@Valid @RequestBody CreateWalletRequest request) {
        return ResponseEntity.ok(walletService.createWallet(request));
    }
    @GetMapping
    public ResponseEntity<List<Wallet>> getAll() {
        return ResponseEntity.ok(walletService.getAllWallets());
    }
    @GetMapping("/{id}")
    public ResponseEntity<WalletResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(walletService.getWalletById(id));
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<WalletResponse> getByUserId(@PathVariable Integer userId) {
        return ResponseEntity.ok(walletService.getWalletByUserId(userId));
    }
    @GetMapping("/{walletId}")
    public ResponseEntity<WalletResponse> getByWalletId(@PathVariable String walletId) {
        return ResponseEntity.ok(walletService.getWalletByWalletId(walletId));
    }
    //no uso userId porq los users pueden tener más de un wallet
    @PutMapping("/{walletId}/deposit") //no se puede mandar bigdecimal como paramato de url :/
    public ResponseEntity<WalletResponse> deposit( @RequestBody Map<String, Object> requestData) {
        String walletId = (String) requestData.get("walletId");
        BigDecimal newBalance = new BigDecimal(requestData.get("newBalance").toString());
        return ResponseEntity.ok(walletService.deposit(walletId, newBalance));
    }
    @PostMapping("/transfer")
    public ResponseEntity<TransferResponse> transfer(@RequestBody CreateTransferRequest request) {
        return ResponseEntity.ok(walletService.transfer(
                request.senderWalletId(),
                request.receiverWalletId(),
                request.transactionAmount()
        ));
    }
}