package com.keepu.webAPI.exception;

//falta un campo obligatorio (por ejemplo, sin nombre de usuario).

public class MissingFieldException extends RuntimeException {
    public MissingFieldException(String message) {
        super(message);
    }
}
