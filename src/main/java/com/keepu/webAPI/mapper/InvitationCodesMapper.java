package com.keepu.webAPI.mapper;

import com.keepu.webAPI.dto.request.CreateInvitationCodeRequest;
import com.keepu.webAPI.dto.response.InvitationCodeResponse;
import com.keepu.webAPI.model.InvitationCodes;
import com.keepu.webAPI.model.User;
import org.springframework.stereotype.Component;

@Component
public class InvitationCodesMapper {

    public InvitationCodeResponse toInvitationCodeResponse(InvitationCodes invitationCode) {
        if (invitationCode == null) {
            return null;
        }
        return new InvitationCodeResponse(
                invitationCode.getId(),
                invitationCode.getCode(),
                invitationCode.isUsed(),
                invitationCode.getExpiresAt(),
                invitationCode.getUser().getId()
        );
    }

    public InvitationCodes toInvitationCodeEntity(CreateInvitationCodeRequest request, User user) {
        if (request == null || user == null) {
            return null;
        }

        InvitationCodes invitationCode = new InvitationCodes();
        invitationCode.setCode(request.code());
        invitationCode.setExpiresAt(request.expiresAt());
        invitationCode.setUsed(false);
        invitationCode.setUser(user);
        return invitationCode;
    }
}