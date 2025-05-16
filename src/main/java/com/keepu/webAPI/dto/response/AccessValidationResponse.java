// AccessValidationResponse.java
package com.keepu.webAPI.dto.response;

public record AccessValidationResponse(boolean accessGranted, String message) {}
