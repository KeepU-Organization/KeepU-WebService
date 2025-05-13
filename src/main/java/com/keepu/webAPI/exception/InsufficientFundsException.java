package com.keepu.webAPI.exception;

//el usuario intenta gastar más dinero del que tiene.


public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(String message) {
        super(message);
    }
}
