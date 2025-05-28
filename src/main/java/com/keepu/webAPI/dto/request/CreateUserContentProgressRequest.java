package com.keepu.webAPI.dto.request;

import jakarta.validation.constraints.NotNull;

public record CreateUserContentProgressRequest(
        @NotNull(message = "El ID del usuario no puede ser nulo")
        Long userId,

        @NotNull(message = "El ID del contenido no puede ser nulo")
        Integer contentId,

        @NotNull(message = "El porcentaje de progreso no puede ser nulo")
        Double progressPercentage
) {}