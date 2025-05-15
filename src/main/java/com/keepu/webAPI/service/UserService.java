package com.keepu.webAPI.service;

import com.keepu.webAPI.dto.request.*;
import com.keepu.webAPI.dto.response.InvitationCodeResponse;
import com.keepu.webAPI.dto.response.ParentChildrenResponse;
import com.keepu.webAPI.dto.response.ParentResponse;
import com.keepu.webAPI.dto.response.UserResponse;
import com.keepu.webAPI.exception.EmailAlreadyExistsException;
import com.keepu.webAPI.exception.InvalidEmailFormatException;
import com.keepu.webAPI.exception.InvalidInvitationCodeException;
import com.keepu.webAPI.exception.InvalidPasswordFormatException;
import com.keepu.webAPI.mapper.UserMapper;
import com.keepu.webAPI.model.Children;
import com.keepu.webAPI.model.InvitationCodes;
import com.keepu.webAPI.model.Parent;
import com.keepu.webAPI.model.User;
import com.keepu.webAPI.model.enums.UserType;
import com.keepu.webAPI.model.enums.WalletType;
import com.keepu.webAPI.repository.ChildrenRepository;
import com.keepu.webAPI.repository.ParentRepository;
import com.keepu.webAPI.repository.UserRepository;
import com.keepu.webAPI.utils.EmailPasswordValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ChildrenRepository childrenRepository;
    private final ParentRepository parentRepository;
    private final PasswordEncoder passwordEncoder;
    private final WalletService walletService;
    private final InvitationCodesService invitationCodesService;
    private final ParentChildrenService parentChildrenService;

    @Transactional
    public UserResponse registerParent(CreateParentRequest request) {
        String encodedPassword=registerUser(request.email(), request.password());

        User newUser = userMapper.toUserEntity(request);
        newUser.setPassword(encodedPassword);
        newUser.setUserType(UserType.PARENT);
        User savedUser = userRepository.save(newUser);


        Parent parent = userMapper.toParentEntity(request, savedUser);
        Parent savedParent = parentRepository.save(parent);

        //wallet de registro:
        CreateWalletRequest createWalletRequest = new CreateWalletRequest(WalletType.PARENT, newUser.getId());
        walletService.createWallet(createWalletRequest);

        return userMapper.toUserResponse(savedUser, savedParent, null);

    }

    @Transactional
    public UserResponse registerChild(CreateChildrenRequest request) {
        // Validar campos comunes
        String encodedPassword=registerUser(request.email(), request.password());

        //validar la validez del codigo
        if (request.invitationCode() == null || request.invitationCode().isEmpty()) {
            throw new IllegalArgumentException("Invitation code cannot be empty");
        }
        if (!invitationCodesService.isInvitationCodeValid(request.invitationCode())) {
            throw new InvalidInvitationCodeException("Invalid invitation code");
        }

        // Crear y guardar el usuario base
        User newUser = userMapper.toUserEntity(request);
        newUser.setPassword(encodedPassword);
        newUser.setUserType(UserType.CHILD);
        User savedUser = userRepository.save(newUser);

        // Crear el Child
        Children child = userMapper.toChildEntity(request, savedUser);

        // Guardar el Child
        Children savedChild = childrenRepository.save(child);

        //wallet de registro:
        CreateWalletRequest createWalletRequest = new CreateWalletRequest(WalletType.STANDARD, newUser.getId());
        walletService.createWallet(createWalletRequest);

        //poner el codigo de registro como usado
        invitationCodesService.updateInvitationCode(request.invitationCode());

        //agregar a tabla parentChildren
        InvitationCodeResponse invitationCodeResponse = invitationCodesService.getInvitationCodeByCode(request.invitationCode());
        UserResponse parentResponse=getUserById(invitationCodeResponse.userId());
        Parent parent = parentRepository.findById(parentResponse.id())
                .orElseThrow(() -> new IllegalArgumentException("Parent not found"));

        CreateParentChildrenRequest createParentChildrenRequest = new CreateParentChildrenRequest(parent.getId(), savedChild.getId());
        parentChildrenService.createParentChildren(createParentChildrenRequest);

        return userMapper.toUserResponse(savedUser, null, savedChild);
    }

    public String registerUser(String email, String password) {
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
        return passwordEncoder.encode(password);
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