package com.keepu.webAPI.mapper;

import com.keepu.webAPI.dto.request.CreateAuthCodeRequest;
import com.keepu.webAPI.dto.response.AuthCodeResponse;
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
                authCode.getUser().getId(),
                authCode.getCode(),
                authCode.getCodeType().name(),
                authCode.isUsed(),
                authCode.getExpiresAt()
        );
    }

    public AuthCode toAuthCodeEntity(CreateAuthCodeRequest request, User user) {
        if (request == null || user == null) {
            return null;
        }

        AuthCode authCode = new AuthCode();
        authCode.setUser(user);
        return authCode;
    }
}