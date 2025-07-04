package com.keepu.webAPI.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateModuleRequest(
        @NotBlank(message = "El título no puede estar vacío")
        String title,

        @NotBlank(message = "La descripción no puede estar vacía")
        String description,

        @NotNull(message = "El índice de orden no puede ser nulo")
        Integer orderIndex,

        @NotNull(message = "El codigo del curso no puede ser nulo")
        String courseCode,
        String imageUrl,
        @NotNull(message = "La duración no puede ser nula")
        Integer duration,
        @NotNull(message = "El código no puede ser nulo")
        String code
) {}