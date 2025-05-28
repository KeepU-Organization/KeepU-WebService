package com.keepu.webAPI.mapper;

import com.keepu.webAPI.dto.request.CreateParentChildrenRequest;
import com.keepu.webAPI.dto.response.ParentChildrenResponse;
import com.keepu.webAPI.model.Children;
import com.keepu.webAPI.model.Parent;
import com.keepu.webAPI.model.ParentChildren;
import com.keepu.webAPI.model.ParentChildrenId;
import org.springframework.stereotype.Component;

@Component
public class ParentChildrenMapper {

    public ParentChildrenResponse toParentChildrenResponse(ParentChildren parentChildren) {
        if (parentChildren == null) {
            return null;
        }
        return new ParentChildrenResponse(
                parentChildren.getParent().getUser().getId(),
                parentChildren.getChild().getUser().getId(),
                parentChildren.getRelationshipType()
        );
    }

    public ParentChildren toParentChildrenEntity(CreateParentChildrenRequest request, Parent parent, Children child) {
        if (request == null || parent == null || child == null) {
            return null;
        }

        ParentChildren parentChildren = new ParentChildren();
        parentChildren.setId(new ParentChildrenId(parent.getUser().getId(), child.getUser().getId()));
        parentChildren.setParent(parent);
        parentChildren.setChild(child);
        //parentChildren.setRelationshipType(request.relationshipType());
        return parentChildren;
    }
}