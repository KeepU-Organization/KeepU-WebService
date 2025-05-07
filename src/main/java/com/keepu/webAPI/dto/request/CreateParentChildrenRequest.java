package com.keepu.webAPI.dto.request;

import jakarta.validation.constraints.NotNull;

public record CreateParentChildrenRequest(
        @NotNull(message = "Parent ID cannot be null")
        Integer parentId,

        @NotNull(message = "Child ID cannot be null")
        Integer childId,

        String relationshipType
) {}