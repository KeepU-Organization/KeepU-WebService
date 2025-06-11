package com.keepu.webAPI.exception;

public class InvalidPasswordException extends RuntimeException {
  public InvalidPasswordException(String message) {
    super(message);
  }
}
