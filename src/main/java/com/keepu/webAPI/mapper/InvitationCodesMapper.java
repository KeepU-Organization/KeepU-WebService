package com.keepu.webAPI.mapper;

import com.keepu.webAPI.dto.request.CreateInvitationCodeRequest;
import com.keepu.webAPI.dto.response.InvitationCodeResponse;
import com.keepu.webAPI.model.InvitationCodes;
import com.keepu.webAPI.model.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class InvitationCodesMapper {

    public InvitationCodeResponse toInvitationCodeResponse(InvitationCodes invitationCode) {
        if (invitationCode == null) {
            return null;
        }
        return new InvitationCodeResponse(
                invitationCode.getId(),
                invitationCode.getInvitationCode(),
                invitationCode.isUsed(),
                invitationCode.getExpiresAt(),
                invitationCode.getUser().getId(),
                invitationCode.getChildName(),
                invitationCode.getChildLastName(),
                invitationCode.getChildAge()
        );
    }

    public InvitationCodes toInvitationCodeEntity(CreateInvitationCodeRequest request, User user) {
        if (request == null || user == null) {
            return null;
        }

        InvitationCodes invitationCode = new InvitationCodes();
        invitationCode.setChildName(request.childName());
        invitationCode.setChildLastName(request.childLastName());
        invitationCode.setChildAge(request.childAge());
        invitationCode.setUser(user);
        return invitationCode;
    }
}