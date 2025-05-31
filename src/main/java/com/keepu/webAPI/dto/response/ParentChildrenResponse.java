package com.keepu.webAPI.dto.response;

import com.keepu.webAPI.model.enums.RelationshipType;

public record ParentChildrenResponse(
        Long parentId,
        Long childId,
        RelationshipType relationshipType
) {}