package com.keepu.webAPI.service;

import com.keepu.webAPI.dto.request.CreateInvitationCodeRequest;
import com.keepu.webAPI.dto.response.InvitationCodeResponse;
import com.keepu.webAPI.exception.NotFoundException;
import com.keepu.webAPI.mapper.InvitationCodesMapper;
import com.keepu.webAPI.model.InvitationCodes;
import com.keepu.webAPI.model.User;
import com.keepu.webAPI.repository.InvitationCodesRepository;
import com.keepu.webAPI.repository.UserRepository;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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
    public InvitationCodeResponse getInvitationCodeByCode(String code) {
        InvitationCodes invitationCode = invitationCodesRepository.findByInvitationCode(code)
                .orElseThrow(() -> new NotFoundException("Código de invitación no encontrado"));
        return invitationCodesMapper.toInvitationCodeResponse(invitationCode);
    }
    @Transactional
    public void deleteInvitationCode(String code) {
        InvitationCodes invitationCode = invitationCodesRepository.findByInvitationCode(code)
                .orElseThrow(() -> new NotFoundException("Código de invitación no encontrado"));
        invitationCodesRepository.delete(invitationCode);
    }
    @Transactional
    public void updateInvitationCode(String code) {
        InvitationCodes invitationCode = invitationCodesRepository.findByInvitationCode(code)
                .orElseThrow(() -> new NotFoundException("Código de invitación no encontrado"));
        invitationCode.setUsed(true);
        invitationCodesRepository.save(invitationCode);
    }

    public boolean isInvitationCodeValid( String s) {
        InvitationCodes invitationCode = invitationCodesRepository.findByInvitationCode(s)
                .orElseThrow(() -> new NotFoundException("Código de invitación no encontrado"));
        return !invitationCode.isUsed() && invitationCode.getExpiresAt().isAfter(LocalDateTime.now());
    }
}