package com.keepu.webAPI.dto.response;

import com.keepu.webAPI.model.User;
import com.keepu.webAPI.model.UserAuth;

public record LoginResponse (
        Long userId,
        String name,
        String email,
        boolean emailVerified,
        String userType,
        String token,
        boolean success,
        String message
){
    public LoginResponse(UserAuth user, String token) {
        this(
                user.getUser().getId(),
                user.getUser().getName(),
                user.getUser().getEmail(),
                user.isEmailVerified(),
                user.getUser().getUserType().toString(),
                token,
                true,
                "Login successful"
        );
    }
}
