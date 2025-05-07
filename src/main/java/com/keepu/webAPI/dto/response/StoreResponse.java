package com.keepu.webAPI.dto.response;

public record StoreResponse(
        Integer id,
        String name,
        String location,
        boolean isActive
) {}