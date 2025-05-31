package com.keepu.webAPI.dto.request;

import com.keepu.webAPI.model.enums.RelationshipType;
import jakarta.validation.constraints.NotNull;

public record CreateParentChildrenRequest(
        @NotNull(message = "Parent ID cannot be null")
        Long parentId,

        @NotNull(message = "Child ID cannot be null")
        Long childId

) {}