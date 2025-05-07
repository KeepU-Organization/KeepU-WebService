package com.keepu.webAPI.dto.response;

public record ParentChildrenResponse(
        Integer parentId,
        Integer childId,
        String relationshipType
) {}