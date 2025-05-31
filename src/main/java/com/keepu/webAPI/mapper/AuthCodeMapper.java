package com.keepu.webAPI.mapper;

import com.keepu.webAPI.dto.request.CreateAuthCodeRequest;
import com.keepu.webAPI.dto.response.AuthCodeResponse;
import com.keepu.webAPI.model.UserAuth;
import com.keepu.webAPI.model.enums.AuthCodeType;
import com.keepu.webAPI.model.AuthCode;
import com.keepu.webAPI.model.User;
import org.springframework.stereotype.Component;

@Component
public class AuthCodeMapper {

    public AuthCodeResponse toAuthCodeResponse(AuthCode authCode) {
        if (authCode == null) {
            return null;
        }
        return new AuthCodeResponse(
                authCode.getId(),
                authCode.getUserAuth().getUser() .getId(),
                authCode.getCode(),
                authCode.getCodeType(),
                authCode.isUsed(),
                authCode.getExpiresAt()
        );
    }

    public AuthCode toAuthCodeEntity(CreateAuthCodeRequest request, UserAuth user) {
        if (request == null || user == null) {
            return null;
        }

        AuthCode authCode = new AuthCode();
        authCode.setUserAuth(user);
        authCode.setCodeType(request.authCodeType());
        return authCode;
    }
}