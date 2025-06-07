package com.keepu.webAPI.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateSecurityKeyRequest(
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    String email,
    @NotBlank(message = "Security key cannot be blank")
    @Size(min = 6, max = 20, message = "Security key must be between 6 and 20 characters")
    String securityKey
) {}
