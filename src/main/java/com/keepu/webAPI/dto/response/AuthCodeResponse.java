package com.keepu.webAPI.dto.response;

import com.keepu.webAPI.model.enums.AuthCodeType;

import java.time.LocalDateTime;

public record AuthCodeResponse(
        Long id,
        Long userId,
        String code,
        AuthCodeType codeType,
        boolean isUsed,
        LocalDateTime expiresAt
) {}