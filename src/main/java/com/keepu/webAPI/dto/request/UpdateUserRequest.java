package com.keepu.webAPI.dto.request;

public record UpdateUserRequest(
        String name,
        String lastName,
        String email,
        Boolean enable2FA,
        String password
) {}
