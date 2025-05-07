package com.keepu.webAPI.service;

import com.keepu.webAPI.dto.request.CreateInvitationCodeRequest;
import com.keepu.webAPI.dto.response.InvitationCodeResponse;
import com.keepu.webAPI.exception.NotFoundException;
import com.keepu.webAPI.mapper.InvitationCodesMapper;
import com.keepu.webAPI.model.InvitationCodes;
import com.keepu.webAPI.model.User;
import com.keepu.webAPI.repository.InvitationCodesRepository;
import com.keepu.webAPI.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InvitationCodesService {

    private final InvitationCodesRepository invitationCodesRepository;
    private final UserRepository userRepository;
    private final InvitationCodesMapper invitationCodesMapper;

    @Transactional
    public InvitationCodeResponse createInvitationCode(CreateInvitationCodeRequest request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        InvitationCodes invitationCode = invitationCodesMapper.toInvitationCodeEntity(request, user);
        InvitationCodes savedInvitationCode = invitationCodesRepository.save(invitationCode);

        return invitationCodesMapper.toInvitationCodeResponse(savedInvitationCode);
    }

    @Transactional(readOnly = true)
    public InvitationCodeResponse getInvitationCodeById(Integer id) {
        InvitationCodes invitationCode = invitationCodesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Código de invitación no encontrado"));
        return invitationCodesMapper.toInvitationCodeResponse(invitationCode);
    }
}