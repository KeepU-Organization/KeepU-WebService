package com.keepu.webAPI.controller;

import com.keepu.webAPI.dto.request.CreateBalanceAccessRequest;
import com.keepu.webAPI.dto.response.AccessValidationResponse;
import com.keepu.webAPI.service.BalanceSecurityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/security")
public class BalanceSecurityController {

    @Autowired
    private BalanceSecurityService balanceSecurityService;

    @PostMapping("/validate-access")
    public ResponseEntity<AccessValidationResponse> validateAccess(
            @RequestBody CreateBalanceAccessRequest request) {

        AccessValidationResponse response = balanceSecurityService.validateAccess(
                request.userId(),
                request.passwordOrPin()
        );
        return ResponseEntity.ok(response);
    }
}
