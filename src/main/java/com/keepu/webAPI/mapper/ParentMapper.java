package com.keepu.webAPI.mapper;

import com.keepu.webAPI.dto.request.CreateParentRequest;
import com.keepu.webAPI.dto.response.ParentResponse;
import com.keepu.webAPI.model.Parent;
import com.keepu.webAPI.model.User;
import org.springframework.stereotype.Component;

@Component
public class ParentMapper {

    public ParentResponse toParentResponse(Parent parent) {
        if (parent == null) {
            return null;
        }
        return new ParentResponse(
                parent.getUser().getId(),
                parent.getPhone()
        );
    }

    public Parent toParentEntity(CreateParentRequest request, User user) {
        if (request == null || user == null) {
            return null;
        }

        Parent parent = new Parent();
        parent.setPhone(999999999);
        parent.setUser(user);
        return parent;
    }
}