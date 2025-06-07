package com.keepu.webAPI.controller;

import com.keepu.webAPI.service.PayPalService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/paypal")
@RequiredArgsConstructor
@Slf4j
public class PayPalController {

    private final PayPalService payPalService;

    @PostMapping("/create-order")
    public ResponseEntity<?> createOrder(@RequestBody Map<String, Object> data) throws Exception {
        String amount = String.valueOf(data.get("amount"));
        String token = payPalService.getAccessToken();
        String orderId = payPalService.createOrder(token, amount);

        return ResponseEntity.ok(Map.of("orderId", orderId));
    }
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("PayPal Controller is working!");
    }
}