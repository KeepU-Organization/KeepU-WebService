package com.keepu.webAPI.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateCourseRequest(
        @NotBlank(message = "Title cannot be blank")
        @Size(max = 50, message = "Title cannot exceed 50 characters")
        String title,

        @NotBlank(message = "Description cannot be blank")
        @Size(max = 200, message = "Description cannot exceed 200 characters")
        String description,

        @NotNull(message = "Difficulty level cannot be null")
        int difficultyLevel,

        @NotNull(message = "isPremium cannot be null")
        boolean isPremium,

        @NotBlank(message = "Image URL cannot be blank")
        @Size(max = 255, message = "Image URL cannot exceed 255 characters")
        String imageUrl,
        @NotNull(message = "El código no puede ser nulo")
        String code
) {}