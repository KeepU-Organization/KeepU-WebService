package com.keepu.webAPI.dto.request;

import jakarta.validation.constraints.NotNull;

public record CreateChildrenRequest(
        @NotNull(message = "User ID cannot be null")
        Integer userId,

        @NotNull(message = "Age cannot be null")
        Integer age
) {}