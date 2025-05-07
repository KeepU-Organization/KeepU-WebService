package com.keepu.webAPI.controller;

import com.keepu.webAPI.dto.request.CreateUserBadgeRequest;
import com.keepu.webAPI.dto.response.UserBadgeResponse;
import com.keepu.webAPI.service.UserBadgesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user-badges")
@RequiredArgsConstructor
public class UserBadgesController {

    private final UserBadgesService userBadgesService;

    @PostMapping
    public ResponseEntity<UserBadgeResponse> create(@Valid @RequestBody CreateUserBadgeRequest request) {
        return ResponseEntity.ok(userBadgesService.createUserBadge(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserBadgeResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(userBadgesService.getUserBadgeById(id));
    }
}