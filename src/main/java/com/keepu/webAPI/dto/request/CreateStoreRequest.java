package com.keepu.webAPI.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateStoreRequest(
        @NotBlank(message = "El nombre no puede estar vacío")
        String name,

        @NotBlank(message = "La ubicación no puede estar vacía")
        String location
) {}