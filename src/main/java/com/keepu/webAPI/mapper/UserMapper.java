package com.keepu.webAPI.mapper;

import com.keepu.webAPI.dto.request.CreateUserRequest;
import com.keepu.webAPI.dto.response.UserResponse;
import com.keepu.webAPI.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponse toUserResponse(User user) {
        if (user == null) {
            return null;
        }
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getLastNames(),
                user.getUserType(),
                user.getEmail(),
                user.isHas2FA(),
                user.isAuthenticated(),
                user.isActive()
        );
    }

    public User toUserEntity(CreateUserRequest request) {
        if (request == null) {
            return null;
        }

        User user = new User();
        user.setName(request.name());
        user.setLastNames(request.lastNames());
        user.setUserType(request.userType());
        user.setEmail(request.email());
        user.setPassword(request.password());
        user.setHas2FA(request.has2FA());
        user.setSecurityKey(request.securityKey());
        user.setAuthenticated(false);
        user.setActive(true);
        return user;
    }
}