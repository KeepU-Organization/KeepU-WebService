package com.keepu.webAPI.mapper;

import com.keepu.webAPI.dto.request.CreateChildrenRequest;
import com.keepu.webAPI.dto.request.CreateParentRequest;
import com.keepu.webAPI.dto.response.UserResponse;
import com.keepu.webAPI.model.Children;
import com.keepu.webAPI.model.Parent;
import com.keepu.webAPI.model.User;
import com.keepu.webAPI.model.enums.UserType;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponse toUserResponse(User user, Parent parent, Children child) {
        if (user == null) {
            return null;
        }

        boolean isParent = parent != null;
        boolean isChild = child != null;

        Integer id =user.getId();
        String name=        user.getName();
        String lastNames=        user.getLastNames();
        UserType userType=        user.getUserType();
        String email=        user.getEmail();
        Boolean has2FA=        user.isHas2FA();
        Boolean isAuthenticated=        user.isAuthenticated();
        Boolean isActive=        user.isActive();

        Integer phoneNumber=null;
        if (isParent) {
            phoneNumber = parent.getPhone();
        }
        Integer age = null;
        if (isChild) {
            age = child.getAge();
        }

        return new UserResponse(
            id,
            name,
            lastNames,
            userType,
            email,
            has2FA,
            isAuthenticated,
            isActive,
            user.getCreatedAt(),
            isParent,
            isChild,
            parent != null ? parent.getPhone():null,
            child!= null ? child.getAge():null

        );
    }

    public User toUserEntity(CreateParentRequest request) {
        if (request == null) {
            return null;
        }

        User user = new User();
        user.setName(request.name());
        user.setLastNames(request.lastNames());
        user.setUserType(UserType.PARENT);
        user.setEmail(request.email());
        user.setPassword(request.password());

        user.setHas2FA(false);
        user.setAuthenticated(false);
        user.setActive(true);
        return user;
    }
    public User toUserEntity(CreateChildrenRequest request) {
        if (request == null) {
            return null;
        }
        User user = new User();
        user.setName(request.name());
        user.setLastNames(request.lastNames());
        user.setUserType(UserType.CHILD);
        user.setEmail(request.email());
        user.setPassword(request.password());
        user.setHas2FA(false);
        user.setAuthenticated(false);
        user.setActive(true);
        return user;
    }

    public Parent toParentEntity(CreateParentRequest request, User user) {
        Parent parent = new Parent();
        parent.setUser(user);
        parent.setPhone(999999999);
        return parent;
    }

    /**
     * Crea una entidad Child a partir de la solicitud y el usuario
     */
    public Children toChildEntity(CreateChildrenRequest request, User user) {
        Children child = new Children();
        child.setUser(user);
        child.setAge(99);
        return child;
    }

}