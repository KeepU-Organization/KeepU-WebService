package com.keepu.webAPI.utils;
import java.util.regex.Pattern;
public class EmailPasswordValidator {

    // Patrón para validar emails
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    // Patrón para validar contraseñas
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[0-9]).{8,}$");

    /**
     * Valida si el formato del email es correcto
     * @param email El email a validar
     * @return true si el email es válido, false en caso contrario
     */
    public static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }
    public static boolean isValidPassword(String password) {
        if (password == null) {
            return false;
        }
        return PASSWORD_PATTERN.matcher(password).matches();
    }
}
