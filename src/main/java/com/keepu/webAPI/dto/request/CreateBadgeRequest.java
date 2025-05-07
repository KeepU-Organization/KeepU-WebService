package com.keepu.webAPI.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateBadgeRequest(
        @NotBlank(message = "El nombre no puede estar vacío")
        String name,

        @NotBlank(message = "La descripción no puede estar vacía")
        String description,

        @NotBlank(message = "La URL de la imagen no puede estar vacía")
        String imageUrl,

        @NotNull(message = "El costo en puntos no puede ser nulo")
        Integer pointsCost
) {}