package com.keepu.webAPI.controller;

import com.keepu.webAPI.dto.request.CreateUserContentProgressRequest;
import com.keepu.webAPI.dto.response.UserContentProgressResponse;
import com.keepu.webAPI.service.UserContentProgressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user-content-progress")
@RequiredArgsConstructor
public class UserContentProgressController {

    private final UserContentProgressService userContentProgressService;

    @PostMapping
    public ResponseEntity<UserContentProgressResponse> create(@Valid @RequestBody CreateUserContentProgressRequest request) {
        return ResponseEntity.ok(userContentProgressService.createUserContentProgress(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserContentProgressResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(userContentProgressService.getUserContentProgressById(id));
    }
}