package com.keepu.webAPI.dto.response;

import java.time.LocalDateTime;

public record UserBadgeResponse(
        Integer id,
        Long userId,
        Integer badgeId,
        LocalDateTime earnedAt
) {}