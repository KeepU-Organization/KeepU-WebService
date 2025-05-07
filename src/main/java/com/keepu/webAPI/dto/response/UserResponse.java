package com.keepu.webAPI.dto.response;

import com.keepu.webAPI.enums.UserType;

public record UserResponse(
        Integer id,
        String name,
        String lastNames,
        UserType userType,
        String email,
        boolean has2FA,
        boolean isAuthenticated,
        boolean isActive
) {}