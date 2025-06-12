package com.keepu.webAPI.dto.request;

public record ChangePasswordRequest(
        Long userId,
        String currentPassword,
        String newPassword
) {}