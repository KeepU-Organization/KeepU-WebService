package com.keepu.webAPI.dto.response;

import com.keepu.webAPI.model.enums.StoreType;

public record StoreResponse(
        Integer id,
        String name,
        String location,
        boolean isActive,
        StoreType type, // e.g., "gaming", "clothes", etc.
        String link
) {}