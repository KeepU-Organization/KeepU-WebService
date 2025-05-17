package com.keepu.webAPI.exception;
//reglas de negocio no cumplidas (ej. el monto supera el saldo disponible).
public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}
