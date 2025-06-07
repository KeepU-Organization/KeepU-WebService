package com.keepu.webAPI.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.keepu.webAPI.dto.request.CreateShopRequest;
import com.keepu.webAPI.dto.request.CreateWalletRequest;
import com.keepu.webAPI.dto.request.CreateTransferRequest;
import com.keepu.webAPI.dto.response.ShopResponse;
import com.keepu.webAPI.dto.response.TransferResponse;
import com.keepu.webAPI.dto.response.WalletResponse;
import com.keepu.webAPI.model.Wallet;
import com.keepu.webAPI.service.PayPalService;
import com.keepu.webAPI.service.WalletService;
import io.jsonwebtoken.io.IOException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
@RestController
@RequestMapping("/api/v1/wallets")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;
    private final PayPalService payPalService;

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
    @GetMapping("/user/{userId}") //SOLO UNA WALLET!!!
    public ResponseEntity<WalletResponse> getByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(walletService.getWalletByUserId(userId));
    }
    @GetMapping("/{walletId}")
    public ResponseEntity<WalletResponse> getByWalletId(@PathVariable String walletId) {
        return ResponseEntity.ok(walletService.getWalletByWalletId(walletId));
    }
    //no uso userId porq los users pueden tener más de un wallet
    @PutMapping("/deposit")
    public ResponseEntity<?> deposit(@RequestBody Map<String, Object> requestData) {
        try {
            String orderId = (String) requestData.get("orderId");
            String walletId = (String) requestData.get("walletId");

            String accessToken = payPalService.getAccessToken();
            BigDecimal amount = payPalService.captureOrder(accessToken, orderId);

            WalletResponse walletResponse = walletService.deposit(walletId, amount);

            return ResponseEntity.ok(walletResponse);
        } catch (IOException | InterruptedException | JsonProcessingException e) {
            // Registrar el error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al procesar el pago: " + e.getMessage());
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
    }
    @PostMapping("/transfer")
    public ResponseEntity<TransferResponse> transfer(@RequestBody CreateTransferRequest request) {
        return ResponseEntity.ok(walletService.transfer(
                request.senderWalletId(),
                request.receiverWalletId(),
                request.transactionAmount()
        ));
    }
    @PostMapping("/purchase-gift-card")
    public ResponseEntity<ShopResponse> purchaseGiftCard(@RequestBody CreateShopRequest request) {
        return ResponseEntity.ok(walletService.purchaseGiftCard(
                request.walletId(),
                request.storeId(),
                request.quantity(),
                request.totalPrice()
        ));
    }
}