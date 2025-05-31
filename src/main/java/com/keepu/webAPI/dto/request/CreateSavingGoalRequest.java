package com.keepu.webAPI.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateSavingGoalRequest(
        @NotBlank(message = "El nombre no puede estar vacío")
        String name,

        @NotNull(message = "El monto objetivo no puede ser nulo")
        Double targetAmount,

        @NotNull(message = "El ID del usuario no puede ser nulo")
        Long userId
) {}