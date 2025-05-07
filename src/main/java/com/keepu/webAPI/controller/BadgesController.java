package com.keepu.webAPI.controller;

import com.keepu.webAPI.dto.request.CreateBadgeRequest;
import com.keepu.webAPI.dto.response.BadgeResponse;
import com.keepu.webAPI.service.BadgesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/badges")
@RequiredArgsConstructor
public class BadgesController {

    private final BadgesService badgesService;

    @PostMapping
    public ResponseEntity<BadgeResponse> create(@Valid @RequestBody CreateBadgeRequest request) {
        return ResponseEntity.ok(badgesService.createBadge(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BadgeResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(badgesService.getBadgeById(id));
    }
}