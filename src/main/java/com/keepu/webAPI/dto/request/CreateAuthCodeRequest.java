package com.keepu.webAPI.dto.request;

import com.keepu.webAPI.model.enums.AuthCodeType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.aspectj.weaver.ast.Not;

import java.time.LocalDateTime;

public record CreateAuthCodeRequest(
        @NotNull(message = "El ID del usuario no puede ser nulo")
        Integer userId,

        @NotNull(message="El tipo de codigo no puede ser nulo")
        AuthCodeType authCodeType
) {}