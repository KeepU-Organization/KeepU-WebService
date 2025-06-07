package com.keepu.webAPI.service;

import com.keepu.webAPI.dto.response.LoginResponse;
import com.keepu.webAPI.exception.InvalidCredentialsException;
import com.keepu.webAPI.exception.InvalidEmailFormatException;
import com.keepu.webAPI.exception.InvalidPasswordFormatException;
import com.keepu.webAPI.exception.UserNotFoundException;
import com.keepu.webAPI.model.User;
import com.keepu.webAPI.model.UserAuth;
import com.keepu.webAPI.repository.UserAuthRespository;
import com.keepu.webAPI.repository.UserRepository;
import com.keepu.webAPI.security.JwtTokenProvider;
import com.keepu.webAPI.utils.EmailPasswordValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserAuthRespository userAuthRespository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    @Transactional
    public LoginResponse login(String email, String password) {
        // Validate email and password format
        if (!EmailPasswordValidator.isValidEmail(email)) {
            throw new InvalidEmailFormatException("Formato de correo electrónico inválido");
        }
        if (!EmailPasswordValidator.isValidPassword(password)) {
            throw new InvalidPasswordFormatException("Formato de contraseña inválido");
        }

        // Check if the user exists
        UserAuth user = userAuthRespository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));

        // Check if the password matches
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidCredentialsException("Credenciales inválidas");
        }

        // Generate JWT token
        String token = jwtTokenProvider.generateToken(user);

        return new LoginResponse(user,token);
    }
    @Transactional
    public boolean checkSecurityKey(String email, String securityKey) {
        // Validate email format
        if (!EmailPasswordValidator.isValidEmail(email)) {
            throw new InvalidEmailFormatException("Formato de correo electrónico inválido");
        }

        UserAuth user = userAuthRespository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));

        if (!passwordEncoder.matches(securityKey, user.getSecurityKey())) {
            throw new InvalidCredentialsException("Clave de seguridad inválida");
        }
        return true;
    }
}

