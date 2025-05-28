package com.keepu.webAPI.dto.response;

import java.time.LocalDateTime;

public record InvitationCodeResponse(
        Long id,
        String code,
        boolean isUsed,
        LocalDateTime expiresAt,
        Long userId,
        String childName,
        String childLastName
) {}