package com.keepu.webAPI.dto.response;


import com.keepu.webAPI.model.enums.UserType;

import java.time.LocalDateTime;

public record UserResponse(
        Integer id,
        String name,
        String lastNames,
        UserType userType,
        String email,
        boolean has2FA,
        boolean isAuthenticated,
        boolean isActive,
        boolean darkMode,

        LocalDateTime createdAt,
        Boolean isParent,
        Boolean isChild,
        Integer phoneNumber,
        Integer age
) {
    public record ChildSummary(
            Integer id,
            String name,
            String lastNames,
            String email
    ){}
    public record ParentSummary(
            Integer id,
            String name,
            String lastNames,
            String email
    ) {
    }
}