package com.keepu.webAPI.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record CreateAuthCodeRequest(
        @NotNull(message = "El ID del usuario no puede ser nulo")
        Integer userId,

        @NotBlank(message = "El código no puede estar vacío")
        @Size(min = 6, max = 6, message = "El código debe tener exactamente 6 caracteres")
        String code,

        @NotNull(message = "El tipo de código no puede ser nulo")
        String codeType,

        @NotNull(message = "La fecha de expiración no puede ser nula")
        LocalDateTime expiresAt
) {}