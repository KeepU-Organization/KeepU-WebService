package com.keepu.webAPI.service;

import com.keepu.webAPI.dto.request.CreateAuthCodeRequest;
import com.keepu.webAPI.dto.response.AuthCodeResponse;
import com.keepu.webAPI.exception.NotFoundException;
import com.keepu.webAPI.exception.UsedAuthCodeException;
import com.keepu.webAPI.exception.CodeNotFoundException;
import com.keepu.webAPI.exception.CodeExpiredException;
import com.keepu.webAPI.mapper.AuthCodeMapper;
import com.keepu.webAPI.model.AuthCode;
import com.keepu.webAPI.model.User;
import com.keepu.webAPI.model.UserAuth;
import com.keepu.webAPI.model.enums.AuthCodeType;
import com.keepu.webAPI.repository.AuthCodeRepository;
import com.keepu.webAPI.repository.UserAuthRespository;
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
    private final UserAuthRespository userRepository;
    private final AuthCodeMapper authCodeMapper;

    @Transactional
    public AuthCodeResponse createAuthCode(CreateAuthCodeRequest request) {
        UserAuth user = userRepository.findByUserId(request.userId())
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        AuthCode authCode = authCodeMapper.toAuthCodeEntity(request, user);
        AuthCode savedAuthCode = authCodeRepository.save(authCode);

        System.out.println("Código de verificación generado para " + user.getUser().getEmail() + ": " + savedAuthCode.getCode());

        return authCodeMapper.toAuthCodeResponse(savedAuthCode);
    }
    @Transactional
    public AuthCodeResponse updateAuthCode(String code) {
        AuthCode authCode = authCodeRepository.findByCode(code)
                .orElseThrow(() -> new NotFoundException("Codigo de autenticacion no encontrado"));
        if (authCode.isUsed() || authCode.isExpired()) {
            throw new UsedAuthCodeException("El codigo de autenticacion ya fue utilizado o ha expirado");
        }
        authCode.setUsed(true);
        if(authCode.getCodeType()== AuthCodeType.EMAIL_VERIFICATION){
            authCode.getUserAuth().setEmailVerified(true);
        }

        return authCodeMapper.toAuthCodeResponse(authCodeRepository.save(authCode)) ;
    }
    @Transactional(readOnly = true)
    public List<AuthCode> getAllAuthCodes() {
        return authCodeRepository.findAll();
    }
    @Transactional(readOnly = true)
    public AuthCode getAuthCodeById(Long id, AuthCodeType codeType) {
        return authCodeRepository.findTopByUserIdAndCodeType(id, codeType)
                .orElseThrow(() -> new NotFoundException("Codigo de autenticacion no encontrado"));
    }
    @Transactional(readOnly = true)
    public AuthCode getAuthCodeByCode(String code) {
        return authCodeRepository.findByCode(code)
                .orElseThrow(() -> new NotFoundException("Codigo de autenticacion no encontrado"));
    }
    @Transactional(readOnly = true)
    public boolean validateAuthCode(Long userId, String code) {
        AuthCode authCode = authCodeRepository.findByUserAuthUserIdAndCode(userId, code)
                .orElseThrow(() -> new CodeNotFoundException("Código de autenticación no encontrado"));

        if (authCode.isExpired()) {
            throw new CodeExpiredException("El código de autenticación ha expirado");
        }

        if (authCode.isUsed()) {
            throw new IllegalStateException("El código de autenticación ya fue utilizado");
        }

        // Marcar el código como usado
        authCode.setUsed(true);
        authCodeRepository.save(authCode);

        return true;
    }
}