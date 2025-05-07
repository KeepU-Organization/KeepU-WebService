package com.keepu.webAPI.service;

import com.keepu.webAPI.dto.request.CreateAuthCodeRequest;
import com.keepu.webAPI.dto.response.AuthCodeResponse;
import com.keepu.webAPI.exception.NotFoundException;
import com.keepu.webAPI.mapper.AuthCodeMapper;
import com.keepu.webAPI.model.AuthCode;
import com.keepu.webAPI.model.User;
import com.keepu.webAPI.repository.AuthCodeRepository;
import com.keepu.webAPI.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthCodeService {

    private final AuthCodeRepository authCodeRepository;
    private final UserRepository userRepository;
    private final AuthCodeMapper authCodeMapper;

    @Transactional
    public AuthCodeResponse createAuthCode(CreateAuthCodeRequest request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        AuthCode authCode = authCodeMapper.toAuthCodeEntity(request, user);
        AuthCode savedAuthCode = authCodeRepository.save(authCode);

        return authCodeMapper.toAuthCodeResponse(savedAuthCode);
    }
}