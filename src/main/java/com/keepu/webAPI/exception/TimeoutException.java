package com.keepu.webAPI.exception;
//la API tarda demasiado en responder.
public class TimeoutException extends RuntimeException {
    public TimeoutException(String message) {
        super(message);
    }
}
