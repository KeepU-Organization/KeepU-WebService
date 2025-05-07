package com.keepu.webAPI.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateContentItemRequest(
        @NotBlank(message = "El título no puede estar vacío")
        String title,

        @NotBlank(message = "La descripción no puede estar vacía")
        String description,

        @NotNull(message = "El índice de orden no puede ser nulo")
        Integer orderIndex,

        @NotBlank(message = "La URL no puede estar vacía")
        String url,

        @NotNull(message = "El tipo de contenido no puede ser nulo")
        String contentType,

        @NotNull(message = "El ID del módulo no puede ser nulo")
        Integer moduleId
) {}