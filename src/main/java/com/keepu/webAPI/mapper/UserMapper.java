package com.keepu.webAPI.mapper;

import com.keepu.webAPI.dto.request.CreateChildrenRequest;
import com.keepu.webAPI.dto.request.CreateParentRequest;
import com.keepu.webAPI.dto.response.InvitationCodeResponse;
import com.keepu.webAPI.dto.response.UserResponse;
import com.keepu.webAPI.model.*;
import com.keepu.webAPI.mapper.InvitationCodesMapper;
import com.keepu.webAPI.model.enums.UserType;
import com.keepu.webAPI.service.InvitationCodesService;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final InvitationCodesService invitationCodesService;

    public UserMapper(InvitationCodesService invitationCodesService) {
        this.invitationCodesService = invitationCodesService;
    }

    public UserResponse toUserResponse(User user, Parent parent, Children child) {
        if (user == null) {
            return null;
        }

        boolean isParent = parent != null;
        boolean isChild = child != null;

        Long id =user.getId();
        String name=        user.getName();
        String lastNames=        user.getLastNames();
        UserType userType=        user.getUserType();
        String email=        user.getEmail();

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
            user.getProfilePicture(),
            userType,
            email,
            user.isDarkMode(),
            parent != null ? parent.getPhone():null,
            child!= null ? child.getAge():null

        );
    }

    public UserAuth toUserEntity(CreateParentRequest request) {
        if (request == null) {
            return null;
        }

        User user = new User();
        UserAuth userAuth = new UserAuth();

        user.setName(request.name());
        user.setLastNames(request.lastNames());
        user.setUserType(UserType.PARENT);
        user.setEmail(request.email());
        userAuth.setPassword(request.password());

        userAuth.setHas2FA(false);
        userAuth.setEmailVerified(false);
        userAuth.setUser(user);
        return userAuth;
    }
    public UserAuth toUserEntity(CreateChildrenRequest request) {
        if (request == null) {
            return null;
        }
        User user = new User();
        InvitationCodeResponse invitationCodeResponse = invitationCodesService.getInvitationCodeByCode(request.invitationCode());
        user.setName(invitationCodeResponse.childName());
        user.setLastNames(invitationCodeResponse.childLastName());
        user.setUserType(UserType.CHILD);
        user.setEmail(request.email());

        UserAuth userAuth = new UserAuth();

        userAuth.setPassword(request.password());
        userAuth.setHas2FA(false);
        userAuth.setEmailVerified(false);

        userAuth.setUser(user);

        return userAuth;
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
    public Children toChildEntity(CreateChildrenRequest request, UserAuth userAuth, User user) {
        Children child = new Children();
        child.setUser(user);
        return child;
    }

}