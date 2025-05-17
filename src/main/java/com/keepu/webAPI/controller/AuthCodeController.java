package com.keepu.webAPI.controller;

import com.keepu.webAPI.dto.request.CreateAuthCodeRequest;
import com.keepu.webAPI.dto.response.AuthCodeResponse;
import com.keepu.webAPI.model.AuthCode;
import com.keepu.webAPI.service.AuthCodeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth-codes")
@RequiredArgsConstructor
public class AuthCodeController {

    private final AuthCodeService authCodeService;

    @PostMapping
    public ResponseEntity<AuthCodeResponse> create(@Valid @RequestBody CreateAuthCodeRequest request) {
        return ResponseEntity.ok(authCodeService.createAuthCode(request));
    }
    @PutMapping("/{code}")
    public void update(@PathVariable String code) {
        authCodeService.updateAuthCode(code);
    }
    @GetMapping
    public ResponseEntity<List<AuthCode>> getAll() {
        return ResponseEntity.ok(authCodeService.getAllAuthCodes());
    }
}