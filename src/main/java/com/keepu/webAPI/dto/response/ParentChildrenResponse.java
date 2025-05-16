package com.keepu.webAPI.dto.response;

import com.keepu.webAPI.model.enums.RelationshipType;

public record ParentChildrenResponse(
        Integer parentId,
        Integer childId,
        RelationshipType relationshipType
) {}