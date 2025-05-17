package com.keepu.webAPI.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateLoginRequest (
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    String email,
    @NotBlank(message = "Password cannot be blank")
    String password

){

}
