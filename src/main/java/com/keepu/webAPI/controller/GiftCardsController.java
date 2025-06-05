package com.keepu.webAPI.controller;

import com.keepu.webAPI.dto.request.CreateGiftCardRequest;
import com.keepu.webAPI.dto.response.GiftCardResponse;
import com.keepu.webAPI.service.GiftCardsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/gift-cards")
@RequiredArgsConstructor
public class GiftCardsController {

    private final GiftCardsService giftCardsService;

    @PostMapping
    public ResponseEntity<GiftCardResponse> create(@Valid @RequestBody CreateGiftCardRequest request) {
        return ResponseEntity.ok(giftCardsService.createGiftCard(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GiftCardResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(giftCardsService.getGiftCardById(id));
    }
    @GetMapping()
    public ResponseEntity<List<GiftCardResponse>> getAll() {
        return ResponseEntity.ok(giftCardsService.getAllGiftCards());
    }
}