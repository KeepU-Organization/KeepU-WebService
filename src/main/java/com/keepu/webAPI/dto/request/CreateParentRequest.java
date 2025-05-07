package com.keepu.webAPI.dto.request;

import jakarta.validation.constraints.NotNull;

public record CreateParentRequest(
        @NotNull(message = "Phone cannot be null")
        Integer phone,

        @NotNull(message = "User ID cannot be null")
        Integer userId
) {}