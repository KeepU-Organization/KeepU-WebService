package com.keepu.webAPI.service;

import com.keepu.webAPI.dto.request.*;
import com.keepu.webAPI.dto.response.InvitationCodeResponse;
import com.keepu.webAPI.dto.response.ParentChildrenResponse;
import com.keepu.webAPI.dto.response.ParentResponse;
import com.keepu.webAPI.dto.response.UserResponse;
import com.keepu.webAPI.exception.*;
import com.keepu.webAPI.mapper.UserMapper;
import com.keepu.webAPI.model.*;
import com.keepu.webAPI.model.enums.AuthCodeType;
import com.keepu.webAPI.model.enums.UserType;
import com.keepu.webAPI.model.enums.WalletType;
import com.keepu.webAPI.repository.ChildrenRepository;
import com.keepu.webAPI.repository.ParentRepository;
import com.keepu.webAPI.repository.UserAuthRespository;
import com.keepu.webAPI.exception.InvalidPasswordException;
import com.keepu.webAPI.repository.UserRepository;
import com.keepu.webAPI.utils.EmailPasswordValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.keepu.webAPI.utils.ImageValidator;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

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
    private final AuthCodeService authCodeService;
    private final UserAuthRespository userAuthRespository;

    @Transactional
    public UserResponse registerParent(CreateParentRequest request) {
        String encodedPassword=registerUser(request.email(), request.password());

        UserAuth newUser = userMapper.toUserEntity(request);
        newUser.setPassword(encodedPassword);
        newUser.getUser().setUserType(UserType.PARENT);


        // Asignar imagen por defecto

        String defaultImagePath = "uploads/profilePics/default.jpg";
        newUser.getUser().setProfilePicture(defaultImagePath);

        User savedUser = userRepository.save(newUser.getUser());


        Parent parent = userMapper.toParentEntity(request, savedUser);
        Parent savedParent = parentRepository.save(parent);

        userAuthRespository.save(newUser);


        //wallet de registro:
        CreateWalletRequest createWalletRequest = new CreateWalletRequest(WalletType.PARENT, newUser.getUser().getId());
        walletService.createWallet(createWalletRequest);

        // Crear codigo de autenticacion
        CreateAuthCodeRequest createAuthCodeRequest = new CreateAuthCodeRequest(savedUser.getId(), AuthCodeType.EMAIL_VERIFICATION);
        authCodeService.createAuthCode(createAuthCodeRequest);

        return userMapper.toUserResponse(savedUser, savedParent, null);

    }

    @Transactional
    public UserResponse registerChild(CreateChildrenRequest request) {
        // Validar campos comunes
        String encodedPassword=registerUser(request.email(), request.password());

        //validar la validez del codigo
        if (request.invitationCode() == null || request.invitationCode().isEmpty()) {
            throw new MissingFieldException("Invitation code cannot be empty");
        }
        if (!invitationCodesService.isInvitationCodeValid(request.invitationCode())) {
            throw new InvalidInvitationCodeException("Invalid invitation code");
        }

        // Crear y guardar el usuario base
        UserAuth newUser = userMapper.toUserEntity(request);
        newUser.setPassword(encodedPassword);
        newUser.getUser().setUserType(UserType.CHILD);

        // Asignar imagen por defecto
        String defaultImagePath = "uploads/profilePics/default.jpg";
        newUser.getUser(). setProfilePicture(defaultImagePath);

        User savedUser = userRepository.save(newUser.getUser());

        // Crear el Child
        Children child = userMapper.toChildEntity(request, newUser, savedUser);
        child.setAge(invitationCodesService.getInvitationCodeByCode(request.invitationCode()).childAge());
        // Guardar el Child
        Children savedChild = childrenRepository.save(child);

        newUser.setEmailVerified(true);
        // Guardar el UserAuth
        userAuthRespository.save(newUser);

        //wallet de registro:
        CreateWalletRequest createWalletRequest = new CreateWalletRequest(WalletType.STANDARD, newUser.getUser().getId());
        walletService.createWallet(createWalletRequest);

        //poner el codigo de registro como usado
        invitationCodesService.updateInvitationCode(request.invitationCode());

        //agregar a tabla parentChildren
        InvitationCodeResponse invitationCodeResponse = invitationCodesService.getInvitationCodeByCode(request.invitationCode());
        UserResponse parentResponse=getUserById(invitationCodeResponse.userId());
        Parent parent = parentRepository.findById(parentResponse.id())
                .orElseThrow(() -> new IllegalArgumentException("Parent not found"));

        CreateParentChildrenRequest createParentChildrenRequest = new CreateParentChildrenRequest(parent.getUser().getId(), savedChild.getUser().getId());
        parentChildrenService.createParentChildren(createParentChildrenRequest);

        return userMapper.toUserResponse(savedUser, null, savedChild);
    }

    public String registerUser(String email, String password) {
        // Validar que el email no esté vacío
        if (email== null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email no puede estar vacío.");
        }

        // Validar formato de email
        if (!EmailPasswordValidator.isValidEmail(email)) {
            throw new InvalidEmailFormatException("Formato inválido de email: " + email);
        }

        // Validar que el email no esté ya registrado
        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException("El email " + email + " ya está registrado.");
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
    @Transactional(readOnly = true)
    public UserResponse getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Parent parent = parentRepository.findByUserId(userId);
        Children child = childrenRepository.findByUserId(userId);

        return userMapper.toUserResponse(user, parent, child);
    }
    @Transactional(readOnly = true)
    public List<UserAuth> getAllUsersAuth() {
        return userAuthRespository.findAll();
    }

    @Transactional
    public void updateProfilePicture(Long userId, MultipartFile file) {
        if (!ImageValidator.isValidImage(file)) {
            throw new IllegalArgumentException("La imagen no es válida o supera el tamaño permitido.");
        }

        try {
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            String relativePath = "uploads/profilePics/" + fileName;
            String uploadDir = "src/main/java/com/keepu/webAPI/" + relativePath;

            File directory = new File("src/main/java/com/keepu/webAPI/uploads/profilePics");
            if (!directory.exists()) {
                directory.mkdirs();
            }

            Path filePath = Paths.get(uploadDir);
            Files.write(filePath, file.getBytes());

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

            if (user.getProfilePicture() != null && !user.getProfilePicture().equals("uploads/profilePics/default.png")) {
                Path oldPath = Paths.get("src/main/java/com/keepu/webAPI/", user.getProfilePicture());
                Files.deleteIfExists(oldPath);
            }

            user.setProfilePicture(relativePath);
            userRepository.save(user);
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar la imagen: " + e.getMessage(), e);
        }
    }



    @Transactional
    public void changePassword(ChangePasswordRequest request) {
        // Buscar UserAuth por ID
        UserAuth userAuth = userAuthRespository.findById(request.userId())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado."));

        // Verificar contraseña actual
        if (!passwordEncoder.matches(request.currentPassword(), userAuth.getPassword())) {
            throw new InvalidPasswordException("La contraseña actual no es correcta.");
        }

        // Validar nueva contraseña
        if (!EmailPasswordValidator.isValidPassword(request.newPassword())) {
            throw new InvalidPasswordFormatException(
                    "La nueva contraseña debe tener al menos 8 caracteres, una mayúscula, una minúscula, un número, un carácter especial y sin espacios."
            );
        }

        // Codificar y guardar la nueva contraseña
        String encodedNewPassword = passwordEncoder.encode(request.newPassword());
        userAuth.setPassword(encodedNewPassword);
        userAuthRespository.save(userAuth);
    }



}