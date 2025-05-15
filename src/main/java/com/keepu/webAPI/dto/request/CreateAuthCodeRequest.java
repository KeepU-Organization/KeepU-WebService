package com.keepu.webAPI.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record CreateAuthCodeRequest(
        @NotNull(message = "El ID del usuario no puede ser nulo")
        Integer userId


) {}