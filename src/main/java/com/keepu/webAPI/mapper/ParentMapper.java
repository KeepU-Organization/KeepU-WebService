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
                parent.getId(),
                parent.getPhone(),
                parent.getUser() != null ? parent.getUser().getId() : null
        );
    }

    public Parent toParentEntity(CreateParentRequest request, User user) {
        if (request == null || user == null) {
            return null;
        }

        Parent parent = new Parent();
        parent.setPhone(request.phoneNumber());
        parent.setUser(user);
        return parent;
    }
}