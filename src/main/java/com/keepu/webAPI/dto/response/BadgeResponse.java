package com.keepu.webAPI.dto.response;

public record BadgeResponse(
        Integer id,
        String name,
        String description,
        String imageUrl,
        Integer pointsCost
) {}