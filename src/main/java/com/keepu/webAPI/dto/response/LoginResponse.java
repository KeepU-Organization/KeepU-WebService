package com.keepu.webAPI.dto.response;

import com.keepu.webAPI.model.User;

public record LoginResponse (
    Integer userId,
    String name,
    String email,
    String userType,
    String token,
    boolean success,
    String message

){
    public LoginResponse(User user, String token) {
        this(user.getId(), user.getName(), user.getEmail(), user.getUserType().toString(), token, true, "Login successful");
    }
}