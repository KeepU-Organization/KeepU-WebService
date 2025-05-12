package com.keepu.webAPI.service;

import com.keepu.webAPI.dto.request.CreateChildrenRequest;
import com.keepu.webAPI.dto.request.CreateParentRequest;
import com.keepu.webAPI.dto.response.UserResponse;
import com.keepu.webAPI.exception.EmailAlreadyExistsException;
import com.keepu.webAPI.exception.InvalidEmailFormatException;
import com.keepu.webAPI.exception.InvalidPasswordFormatException;
import com.keepu.webAPI.mapper.UserMapper;
import com.keepu.webAPI.model.Children;
import com.keepu.webAPI.model.Parent;
import com.keepu.webAPI.model.User;
import com.keepu.webAPI.repository.ChildrenRepository;
import com.keepu.webAPI.repository.ParentRepository;
import com.keepu.webAPI.repository.UserRepository;
import com.keepu.webAPI.utils.EmailPasswordValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ChildrenRepository childrenRepository;
    private final ParentRepository parentRepository;

    @Transactional
    public UserResponse registerParent(CreateParentRequest request) {
        registerUser(request.email(), request.password());

        User newUser = userMapper.toUserEntity(request);
        User savedUser = userRepository.save(newUser);

        Parent parent = userMapper.toParentEntity(request, savedUser);
        Parent savedParent = parentRepository.save(parent);

        return userMapper.toUserResponse(savedUser, savedParent, null);

    }

    @Transactional
    public UserResponse registerChild(CreateChildrenRequest request) {
        // Validar campos comunes
        registerUser(request.email(), request.password());


        // Crear y guardar el usuario base
        User newUser = userMapper.toUserEntity(request);
        User savedUser = userRepository.save(newUser);

        // Crear el Child
        Children child = userMapper.toChildEntity(request, savedUser);

        // Guardar el Child
        Children savedChild = childrenRepository.save(child);

        return userMapper.toUserResponse(savedUser, null, savedChild);
    }

    public void registerUser(String email, String password) {
        // Validar que el email no esté vacío
        if (email== null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }

        // Validar formato de email
        if (!EmailPasswordValidator.isValidEmail(email)) {
            throw new InvalidEmailFormatException("Invalid email format: " + email);
        }

        // Validar que el email no esté ya registrado
        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException("The email " + email + " is already registered.");
        }

        // Validar formato de contraseña
        if (!EmailPasswordValidator.isValidPassword(password)) {
            throw new InvalidPasswordFormatException(
                    "Password must be at least 8 characters long and contain at least one digit, " +
                            "one lowercase letter, one uppercase letter, one special character, and no spaces."
            );
        }

    }
    @Transactional
    public UserResponse getUserById(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Parent parent = parentRepository.findByUserId(userId);
        Children child = childrenRepository.findByUserId(userId);

        return userMapper.toUserResponse(user, parent, child);
    }
}