package com.keepu.webAPI.dto.request;

import com.keepu.webAPI.model.enums.UserType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(
        @NotBlank(message = "Name cannot be blank")
        @Size(max = 50, message = "Name cannot exceed 50 characters")
        String name,

        @NotBlank(message = "Last names cannot be blank")
        @Size(max = 100, message = "Last names cannot exceed 100 characters")
        String lastNames,

        @NotNull(message = "User type cannot be null")
        UserType userType,

        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Invalid email format")
        String email,

        @NotBlank(message = "Password cannot be blank")
        @Size(min = 8, message = "Password must be at least 8 characters long")
        String password,

        boolean has2FA,

        //@NotBlank(message = "Security key cannot be blank")
        String securityKey,

        //@NotBlank(message = "Profile picture cannot be blank")
        String profilePicture

) {}