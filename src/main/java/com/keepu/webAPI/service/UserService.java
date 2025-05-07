package com.keepu.webAPI.service;

import com.keepu.webAPI.dto.request.CreateUserRequest;
import com.keepu.webAPI.dto.response.UserResponse;
import com.keepu.webAPI.exception.EmailAlreadyExistsException;
import com.keepu.webAPI.mapper.UserMapper;
import com.keepu.webAPI.model.User;
import com.keepu.webAPI.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public UserResponse registerUser(CreateUserRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new EmailAlreadyExistsException("The email " + request.email() + " is already registered.");
        }

        User newUser = userMapper.toUserEntity(request);
        User savedUser = userRepository.save(newUser);
        return userMapper.toUserResponse(savedUser);
    }
}