package com.keepu.webAPI.exception;

// El usuario intenta gastar más dinero del que tiene.

public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(String message) {
        super(message);
    }
}

