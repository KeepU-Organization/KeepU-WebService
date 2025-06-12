package com.keepu.webAPI.service;

import com.keepu.webAPI.dto.response.LoginResponse;
import com.keepu.webAPI.exception.InvalidCredentialsException;
import com.keepu.webAPI.exception.UserNotFoundException;
import com.keepu.webAPI.model.User;
import com.keepu.webAPI.model.UserAuth;
import com.keepu.webAPI.model.enums.UserType;
import com.keepu.webAPI.repository.UserAuthRespository;
import com.keepu.webAPI.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
//
import com.keepu.webAPI.exception.InvalidEmailFormatException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthServiceUnitTest {

    @Mock
    private UserAuthRespository userAuthRespository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("CP14.1 - Login exitoso con credenciales correctas")
    void login_validCredentials_shouldReturnToken() {
        // Arrange
        String email = "user1@example.com";
        String password = "SecureP@ssword123";
        String token = "jwt...";
        User user = new User();
        user.setId(1L);
        user.setEmail(email);
        user.setName("User One");
        user.setUserType(UserType.PARENT); // Aseguramos que userType no sea null

        UserAuth userAuth = new UserAuth();
        userAuth.setUserId(1L);
        userAuth.setUser(user);
        userAuth.setPassword("encodedPassword");

        when(userAuthRespository.findByEmail(email)).thenReturn(java.util.Optional.of(userAuth));
        when(passwordEncoder.matches(password, userAuth.getPassword())).thenReturn(true);
        when(jwtTokenProvider.generateToken(userAuth)).thenReturn(token);

        // Act
        LoginResponse response = authService.login(email, password);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.userId());
        assertEquals(token, response.token());
        assertEquals("User One", response.name());
        assertEquals("user1@example.com", response.email());
        assertEquals("PARENT", response.userType());
        verify(userAuthRespository, times(1)).findByEmail(email);
        verify(passwordEncoder, times(1)).matches(password, userAuth.getPassword());
        verify(jwtTokenProvider, times(1)).generateToken(userAuth);
    }

    @Test
    @DisplayName("CP14.2 - Login con email no registrado")
    void login_emailNotFound_shouldThrowException() {
        // Arrange
        String email = "notfound@example.com";
        String password = "ValidP@ssword123";

        when(userAuthRespository.findByEmail(email)).thenReturn(java.util.Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> authService.login(email, password));
        verify(userAuthRespository, times(1)).findByEmail(email);
    }

    @Test
    @DisplayName("CP14.3 - Login con contraseña incorrecta")
    void login_invalidPassword_shouldThrowException() {
        // Arrange
        String email = "user1@example.com";
        String password = "ValidP@ssword123";
        User user = new User();
        user.setId(1L);
        user.setEmail(email);

        UserAuth userAuth = new UserAuth();
        userAuth.setUserId(1L);
        userAuth.setUser(user);
        userAuth.setPassword("encodedPassword");

        when(userAuthRespository.findByEmail(email)).thenReturn(java.util.Optional.of(userAuth));
        when(passwordEncoder.matches(password, userAuth.getPassword())).thenReturn(false);

        // Act & Assert
        assertThrows(InvalidCredentialsException.class, () -> authService.login(email, password));
        verify(userAuthRespository, times(1)).findByEmail(email);
        verify(passwordEncoder, times(1)).matches(password, userAuth.getPassword());
    }

    @Test
    @DisplayName("CP7.1 - Validar clave de seguridad correcta")
    void validateSecurityKey_validKey_shouldReturnTrue() {
        // Arrange
        String email = "user1@example.com";
        String securityKey = "Validkey123";
        User user = new User();
        user.setId(1L);
        user.setEmail(email);

        UserAuth userAuth = new UserAuth();
        userAuth.setUserId(1L);
        userAuth.setUser(user);
        userAuth.setSecurityKey("encodedKey");
        when(userAuthRespository.findByEmail(email)).thenReturn(java.util.Optional.of(userAuth));
        when(passwordEncoder.matches(securityKey, userAuth.getSecurityKey())).thenReturn(true);

        boolean result = authService.checkSecurityKey(email, securityKey);

        assertTrue(result);
        verify(userAuthRespository, times(1)).findByEmail(email);
        verify(passwordEncoder, times(1)).matches(securityKey, userAuth.getSecurityKey());
    }
    @Test
    @DisplayName("CP 7.2 - Validar clave de seguridad incorrecta")
    void checkSecurityKey_invalidKey_shouldThrowException() {
        String email = "user1@example.com";
        String securityKey = "WrongKey";
        User user = new User();
        user.setId(1L);
        user.setEmail(email);

        UserAuth userAuth = new UserAuth();
        userAuth.setUserId(1L);
        userAuth.setUser(user);
        userAuth.setSecurityKey("encodedKey");

        when(userAuthRespository.findByEmail(email)).thenReturn(java.util.Optional.of(userAuth));
        when(passwordEncoder.matches(securityKey, userAuth.getSecurityKey())).thenReturn(false);

        assertThrows(InvalidCredentialsException.class, () -> authService.checkSecurityKey(email, securityKey));
        verify(userAuthRespository, times(1)).findByEmail(email);
        verify(passwordEncoder, times(1)).matches(securityKey, userAuth.getSecurityKey());
    }

    @Test
    @DisplayName("CP 7.3 - Validar clave de seguridad con email de formato inválido")
    void checkSecurityKey_invalidEmailFormat_shouldThrowException() {
        String invalidEmail = "correo-invalido";
        String securityKey = "AnyKey";

        assertThrows(InvalidEmailFormatException.class, () -> authService.checkSecurityKey(invalidEmail, securityKey));
        verify(userAuthRespository, never()).findByEmail(anyString());
    }

}