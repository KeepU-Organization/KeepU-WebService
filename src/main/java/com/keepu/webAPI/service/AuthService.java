package com.keepu.webAPI.service;

import com.keepu.webAPI.dto.response.LoginResponse;
import com.keepu.webAPI.exception.InvalidCredentialsException;
import com.keepu.webAPI.exception.InvalidEmailFormatException;
import com.keepu.webAPI.exception.InvalidPasswordFormatException;
import com.keepu.webAPI.exception.UserNotFoundException;
import com.keepu.webAPI.model.User;
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
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Transactional
    public LoginResponse login(String email, String password) {
        // Validate email and password format
        if (!EmailPasswordValidator.isValidEmail(email)) {
            throw new InvalidEmailFormatException("Invalid email format");
        }
        if (!EmailPasswordValidator.isValidPassword(password)) {
            throw new InvalidPasswordFormatException("Invalid password format");
        }

        // Check if the user exists
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        // Check if the password matches
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidCredentialsException("Invalid credentials");
        }

        // Generate JWT token
        String token = jwtTokenProvider.generateToken(user);

        return new LoginResponse(user,token);
    }

}

