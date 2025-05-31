package com.keepu.webAPI.mapper;

import com.keepu.webAPI.dto.request.CreateChildrenRequest;
import com.keepu.webAPI.dto.response.ChildrenResponse;
import com.keepu.webAPI.model.Children;
import com.keepu.webAPI.model.User;
import org.springframework.stereotype.Component;

@Component
public class ChildrenMapper {

    public ChildrenResponse toChildrenResponse(Children children) {
        if (children == null) {
            return null;
        }
        return new ChildrenResponse(
                children.getUser(). getId(),
                children.getAge()
        );
    }

    public Children toChildrenEntity(CreateChildrenRequest request, User user) {
        if (request == null || user == null) {
            return null;
        }

        Children children = new Children();
        children.setUser(user);
        children.setAge(99);
        return children;
    }
}