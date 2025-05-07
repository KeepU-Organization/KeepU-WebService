package com.keepu.webAPI.dto.request;

import jakarta.validation.constraints.NotNull;

public record CreateUserCourseProgressRequest(
        @NotNull(message = "El ID del usuario no puede ser nulo")
        Integer userId,

        @NotNull(message = "El ID del curso no puede ser nulo")
        Integer courseId,

        @NotNull(message = "El porcentaje de progreso no puede ser nulo")
        Double progressPercentage,

        @NotNull(message = "El estado de completado no puede ser nulo")
        Boolean isCompleted
) {}