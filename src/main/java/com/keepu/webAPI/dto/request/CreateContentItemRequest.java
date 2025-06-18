package com.keepu.webAPI.dto.request;

import com.keepu.webAPI.model.enums.ContentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateContentItemRequest(
        @NotBlank(message = "El título no puede estar vacío")
        String title,

        @NotBlank(message = "La descripción no puede estar vacía")
        String description,

        @NotNull(message = "El índice de orden no puede ser nulo")
        Integer orderIndex,

        String url, // URL opcional para el contenido, puede ser nulo si no aplica
        String contentData, // Datos opcionales del contenido, puede ser nulo si no aplica

        @NotNull(message = "El tipo de contenido no puede ser nulo")
        ContentType contentType,

        @NotNull(message = "El codigo del módulo no puede ser nulo")
        String moduleCode,
        String imageUrl,
        Integer duration, // Duración en minutos, puede ser nulo si no aplica,
        @NotNull(message = "El código del contenido no puede ser nulo")
        String code // Código único para el contenido, puede ser utilizado para identificación o enlace
) {}