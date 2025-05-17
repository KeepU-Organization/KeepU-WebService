package com.keepu.webAPI.service;

import com.keepu.webAPI.dto.response.AccessValidationResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class BalanceSecurityService {

    // Simulación almacenamiento en memoria de:
    // - contraseñas (en realidad, deberían estar cifradas y en DB)
    // - intentos fallidos por usuario
    // - bloqueo por tiempo
    private Map<Long, String> userPasswords = new HashMap<>();
    private Map<Long, Integer> failedAttempts = new HashMap<>();
    private Map<Long, LocalDateTime> blockedUntil = new HashMap<>();

    private static final int MAX_ATTEMPTS = 5;
    private static final int BLOCK_MINUTES = 60 * 24 * 2; // 2 días en minutos

    public BalanceSecurityService() {
        // ejemplo: usuario 1 tiene contraseña "1234"
        userPasswords.put(1L, "1234");
        userPasswords.put(2L, "4321");
    }

    public AccessValidationResponse validateAccess(Long userId, String passwordOrPin) {
        // Revisar si está bloqueado
        if (isBlocked(userId)) {
            return new AccessValidationResponse(false, "Cuenta bloqueada por intentos fallidos. Intente más tarde.");
        }

        // Validar contraseña
        String correctPassword = userPasswords.get(userId);
        if (correctPassword == null) {
            return new AccessValidationResponse(false, "Usuario no encontrado.");
        }

        if (correctPassword.equals(passwordOrPin)) {
            resetFailedAttempts(userId);
            return new AccessValidationResponse(true, "Acceso concedido. Monto mostrado.");
        } else {
            registerFailedAttempt(userId);
            int attemptsLeft = MAX_ATTEMPTS - failedAttempts.getOrDefault(userId, 0);
            if (attemptsLeft <= 0) {
                blockUser(userId);
                return new AccessValidationResponse(false, "Cuenta bloqueada por 2 días debido a intentos fallidos.");
            } else {
                return new AccessValidationResponse(false, "Contraseña incorrecta. Intentos restantes: " + attemptsLeft);
            }
        }
    }

    private boolean isBlocked(Long userId) {
        LocalDateTime blockedTime = blockedUntil.get(userId);
        if (blockedTime == null) return false;
        if (LocalDateTime.now().isAfter(blockedTime)) {
            blockedUntil.remove(userId);
            failedAttempts.remove(userId);
            return false;
        }
        return true;
    }

    private void registerFailedAttempt(Long userId) {
        failedAttempts.put(userId, failedAttempts.getOrDefault(userId, 0) + 1);
    }

    private void resetFailedAttempts(Long userId) {
        failedAttempts.remove(userId);
    }

    private void blockUser(Long userId) {
        blockedUntil.put(userId, LocalDateTime.now().plusMinutes(BLOCK_MINUTES));
        failedAttempts.remove(userId);
    }
}
