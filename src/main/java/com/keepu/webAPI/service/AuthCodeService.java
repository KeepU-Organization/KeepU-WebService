package com.keepu.webAPI.service;

import com.keepu.webAPI.dto.request.CreateAuthCodeRequest;
import com.keepu.webAPI.dto.response.AuthCodeResponse;
import com.keepu.webAPI.exception.NotFoundException;
import com.keepu.webAPI.exception.UsedAuthCodeException;
import com.keepu.webAPI.mapper.AuthCodeMapper;
import com.keepu.webAPI.model.AuthCode;
import com.keepu.webAPI.model.User;
import com.keepu.webAPI.model.enums.AuthCodeType;
import com.keepu.webAPI.repository.AuthCodeRepository;
import com.keepu.webAPI.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
    @Transactional
    public void updateAuthCode(String code) {
        AuthCode authCode = authCodeRepository.findByCode(code)
                .orElseThrow(() -> new NotFoundException("Codigo de autenticacion no encontrado"));
        if (authCode.isUsed() || authCode.isExpired()) {
            throw new UsedAuthCodeException("El codigo de autenticacion ya fue utilizado o ha expirado");
        }
        authCode.setUsed(true);
        if(authCode.getCodeType()== AuthCodeType.EMAIL_VERIFICATION){
            authCode.getUser().setAuthenticated(true);
        }

        authCodeRepository.save(authCode);
    }
    @Transactional
    public List<AuthCode> getAllAuthCodes() {
        return authCodeRepository.findAll();
    }
}