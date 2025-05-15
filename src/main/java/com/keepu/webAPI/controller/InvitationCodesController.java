package com.keepu.webAPI.controller;

import com.keepu.webAPI.dto.request.CreateInvitationCodeRequest;
import com.keepu.webAPI.dto.response.InvitationCodeResponse;
import com.keepu.webAPI.service.InvitationCodesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/invitation-codes")
@RequiredArgsConstructor
public class InvitationCodesController {

    private final InvitationCodesService invitationCodesService;

    @PostMapping
    public ResponseEntity<InvitationCodeResponse> create(@Valid @RequestBody CreateInvitationCodeRequest request) {
        return ResponseEntity.ok(invitationCodesService.createInvitationCode(request));
    }

    @GetMapping("/{code}")
    public ResponseEntity<InvitationCodeResponse> getById(@PathVariable String code) {
        return ResponseEntity.ok(invitationCodesService.getInvitationCodeByCode(code));
    }
}