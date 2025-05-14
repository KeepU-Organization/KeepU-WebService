package com.keepu.webAPI.dto.response;

import com.keepu.webAPI.model.User;

public class LoginResponse {
    private Integer userId;
    private String name;
    private String email;
    private String userType;
    private String token;
    private boolean success;
    private String message;

    public LoginResponse(){}
    public LoginResponse(User user, String token) {
        this.userId = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.userType = String.valueOf(user.getUserType());
        this.token = token;
        this.success = true;
        this.message = "Login exitoso";
    }

    public LoginResponse(boolean success, String message) {
        this.success = false;
        this.message = message;
    }

    public LoginResponse(String token) {
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
