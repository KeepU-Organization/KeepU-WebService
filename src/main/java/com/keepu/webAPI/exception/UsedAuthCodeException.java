package com.keepu.webAPI.exception;

public class UsedAuthCodeException extends RuntimeException {
    public UsedAuthCodeException(String message) {
        super(message);
    }
}
