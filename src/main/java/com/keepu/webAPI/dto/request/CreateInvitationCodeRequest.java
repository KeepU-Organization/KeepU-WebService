package com.keepu.webAPI.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record CreateInvitationCodeRequest(
        @NotBlank(message = "El código no puede estar vacío")
        String code,

        @NotNull(message = "La fecha de expiración no puede ser nula")
        LocalDateTime expiresAt,

        @NotNull(message = "El ID del usuario no puede ser nulo")
        Integer userId
) {}