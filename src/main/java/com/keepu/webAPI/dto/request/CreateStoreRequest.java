package com.keepu.webAPI.dto.request;

import com.keepu.webAPI.model.enums.StoreType;
import jakarta.validation.constraints.NotBlank;

public record CreateStoreRequest(
        @NotBlank(message = "El nombre no puede estar vacío")
        String name,

        @NotBlank(message = "La ubicación no puede estar vacía")
        String location,

        @NotBlank(message = "El tipo no puede estar vacío")
        StoreType type,

        @NotBlank(message = "El enlace no puede estar vacío")
        String link
) {}