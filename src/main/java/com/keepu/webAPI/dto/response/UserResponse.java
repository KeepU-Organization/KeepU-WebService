package com.keepu.webAPI.dto.response;


import com.keepu.webAPI.model.enums.UserType;

import java.time.LocalDateTime;

public record UserResponse(
        Long id,
        String name,
        String lastNames,
        String profilePicture,
        UserType userType,
        String email,

        boolean darkMode,

        Integer phoneNumber,
        Integer age
) {
    public record ChildSummary(
            Long id,
            String name,
            String lastNames,
            String email,
            Integer age
    ){}
    public record ParentSummary(
            Long id,
            String name,
            String lastNames,
            String email
    ) {
    }
    public record MeResponse( //response para el frontend del usuario autenticado
            Long id,
            String name,
            String lastNames,
            UserType userType,
            String email,

            boolean has2FA,
            boolean isEmailVerified,

            boolean darkMode,
            Integer phoneNumber,
            Integer age,
            String profilePicture
    ) {
    }
}
