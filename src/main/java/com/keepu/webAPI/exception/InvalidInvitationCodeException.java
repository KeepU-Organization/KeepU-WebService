package com.keepu.webAPI.exception;

public class InvalidInvitationCodeException extends RuntimeException {
    public InvalidInvitationCodeException(String message) {
        super(message);
    }
}
