package com.keepu.webAPI.dto.response;

import java.time.LocalDateTime;

public record InvitationCodeResponse(
        Integer id,
        String code,
        boolean isUsed,
        LocalDateTime expiresAt,
        Integer userId
) {}