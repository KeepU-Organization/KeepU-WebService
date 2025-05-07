package com.keepu.webAPI.dto.response;

import java.time.LocalDateTime;

public record AuthCodeResponse(
        Integer id,
        Integer userId,
        String code,
        String codeType,
        boolean isUsed,
        LocalDateTime expiresAt
) {}