package com.keepu.webAPI.dto.request;

import jakarta.validation.constraints.NotNull;

public record CreateUserBadgeRequest(
        @NotNull(message = "El ID del usuario no puede ser nulo")
        Long userId,

        @NotNull(message = "El ID de la insignia no puede ser nulo")
        Integer badgeId
) {}