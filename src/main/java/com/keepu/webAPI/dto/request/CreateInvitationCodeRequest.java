package com.keepu.webAPI.dto.request;

import jakarta.validation.constraints.NotNull;

public record CreateInvitationCodeRequest(
        @NotNull(message = "El ID del usuario no puede ser nulo")
        Long userId,

        @NotNull(message="El nombre del hijo no puede se null")
        String childName,
        @NotNull(message="El apellido del hijo no puede ser nulo")
        String childLastName,
        @NotNull(message="La edad del hijo no puede ser nulo")
        Integer childAge
) {}