package com.keepu.webAPI.service;

import com.keepu.webAPI.dto.request.*;
import com.keepu.webAPI.dto.response.*;
import com.keepu.webAPI.exception.*;
import com.keepu.webAPI.mapper.UserMapper;
import com.keepu.webAPI.model.Children;
import com.keepu.webAPI.model.Parent;
import com.keepu.webAPI.model.User;
import com.keepu.webAPI.model.UserAuth;
import com.keepu.webAPI.model.enums.AuthCodeType;
import com.keepu.webAPI.model.enums.RelationshipType;
import com.keepu.webAPI.model.enums.UserType;
import com.keepu.webAPI.model.enums.WalletType;
import com.keepu.webAPI.repository.ChildrenRepository;
import com.keepu.webAPI.repository.ParentRepository;
import com.keepu.webAPI.repository.UserAuthRespository;
import com.keepu.webAPI.repository.UserRepository;
import com.keepu.webAPI.utils.EmailPasswordValidator;
import com.keepu.webAPI.utils.ImageValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceUnitTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private ChildrenRepository childrenRepository;
    @Mock
    private ParentRepository parentRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserAuthRespository userAuthRespository;
    @Mock
    private WalletService walletService;
    @Mock
    private AuthCodeService authCodeService;




    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("CP01 - Registrar un padre con datos válidos")
    void createParent_validData_shouldCreateParent() {
        // Arrange
        CreateParentRequest request = new CreateParentRequest(
                "John",
                "Doe",
                "johndoe@gmail.com",
                "P#ssword123");
        User user = new User();
        user.setId(1L);
        user.setName("John");
        user.setLastNames("Doe");
        user.setEmail("johndoe@gmail.com");
        user.setUserType(UserType.PARENT);

        UserAuth userAuth = new UserAuth();
        userAuth.setUserId(1L);
        userAuth.setUser(user);
        userAuth.setPassword("encodedPassword");
        userAuth.setCreatedAt(LocalDateTime.now());
        userAuth.setSecurityKey("encodedPassword");


        Parent parent = new Parent();
        parent.setUserId(1L);
        parent.setUser(user);
        parent.setPhone(987654321);

        CreateWalletRequest createWalletRequest = new CreateWalletRequest(
                WalletType.PARENT,
                1L
        );
        CreateAuthCodeRequest createAuthCodeRequest = new CreateAuthCodeRequest(
                user.getId(),
                AuthCodeType.EMAIL_VERIFICATION
        );

        UserResponse expectedResponse = new UserResponse(
                1L,
                "John",
                "Doe",
                "uploads/profilePics/default.jpg",
                UserType.PARENT,
                "johndoe@gmail.com",
                false,
                987654321,
                null
        );

        when(userRepository.existsByEmail("johndoe@gmail.com")).thenReturn(false);
        when(userRepository.save(user)).thenReturn(user);
        when(parentRepository.save(parent)).thenReturn(parent);
        when(userAuthRespository.save(userAuth)).thenReturn(userAuth);

        when(userMapper.toUserEntity(request)).thenReturn(userAuth);
        when(userMapper.toParentEntity(request, user)).thenReturn(parent);
        when(userMapper.toUserResponse(user, parent, null)).thenReturn(expectedResponse);

        when(passwordEncoder.encode("P#ssword123")).thenReturn("encodedPassword");

        when(walletService.createWallet(createWalletRequest)).thenReturn(new WalletResponse(1, "W-123456",WalletType.PARENT, BigDecimal.ZERO,1L));
        when(authCodeService.createAuthCode(createAuthCodeRequest)).thenReturn(new AuthCodeResponse(1L, 1L,"authCode123", AuthCodeType.EMAIL_VERIFICATION, false, LocalDateTime.now()));
        // Act
        UserResponse response = userService.registerParent(request);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.id());
        assertEquals("John", response.name());
        assertEquals("Doe", response.lastNames());
        assertEquals("johndoe@gmail.com", response.email());
        assertEquals(UserType.PARENT, response.userType());

        verify(userRepository, times(1)).existsByEmail("johndoe@gmail.com");
        verify(userRepository, times(1)).save(user);
        verify(parentRepository, times(1)).save(parent);
        verify(userAuthRespository, times(1)).save(userAuth);
        verify(walletService, times(1)).createWallet(createWalletRequest);

    }

    @Test
    @DisplayName("CP02 - Registrar un padre con email ya existente")
    void createParent_emailAlreadyExists_shouldThrowException() {
        // Arrange
        CreateParentRequest request = new CreateParentRequest(
                "John",
                "Doe",
                "johndoe@gmail.com",
                "P#ssword123"
        );
        when(userRepository.existsByEmail("johndoe@gmail.com")).thenReturn(true);
        // Act & Assert
        assertThrows(EmailAlreadyExistsException.class, () -> userService.registerParent(request));
    }
    @Test
    @DisplayName("CP03 - Registrar un padre con formato de correo electrónico inválido")
    void createParent_invalidEmailFormat_shouldThrowException() {
        // Arrange
        CreateParentRequest request = new CreateParentRequest(
                "John",
                "Doe",
                "invalid-email-format",
                "P#ssword123"
        );
        // Act & Assert
        assertThrows(InvalidEmailFormatException.class, () -> userService.registerParent(request));
    }
    @Test
    @DisplayName("CP04 - Registrar un padre con contraseña inválida")
    void createParent_invalidPasswordFormat_shouldThrowException() {
        // Arrange
        CreateParentRequest request = new CreateParentRequest(
                "John",
                "Doe",
                "johndoe@gmail.com",
                "short"
        );
        // Act & Assert
        assertThrows(InvalidPasswordFormatException.class, () -> userService.registerParent(request));
    }




/// /////////////////////////////////////////////// HIJOS
    @Mock
    private InvitationCodesService invitationCodesService;
    @Mock
    private ParentChildrenService parentChildrenService;

    @Test
    @DisplayName("CP05 - Registrar un hijo con datos válidos")
    void createChildren_validData_shouldCreateChildren() {
        // Arrange
        CreateChildrenRequest parentRequest = new CreateChildrenRequest(
                "janedoe@gmail.com",
                "P#ssword123",
                "invitationCode123");

        User user= new User();
        user.setId(1L);
        user.setName("Jane");
        user.setLastNames("Doe");
        user.setEmail("janedoe@gmail.com");
        user.setUserType(UserType.CHILD);

        UserAuth newUser = new UserAuth();
        newUser.setUserId(1L);
        newUser.setUser(user);
        newUser.setPassword("encodedPassword");
        newUser.setCreatedAt(LocalDateTime.now());
        newUser.setSecurityKey("encodedPassword");
        newUser.setEmailVerified(true);

        Children children = new Children();
        children.setUserId(1L);
        children.setUser(user);
        children.setAge(15);

        UserResponse expectedResponse = new UserResponse(
                1L,
                "Jane",
                "Doe",
                "uploads/profilePics/default.jpg",
                UserType.CHILD,
                "janedoe@gmail.com",
                false,
                null,
                15);

        InvitationCodeResponse invitationCodeResponse = new InvitationCodeResponse(
                1L, "invitationCode123", false, LocalDateTime.now().plusHours(1), 1L, "Jane", "Doe", 15);

        CreateParentChildrenRequest createParentChildrenRequest = new CreateParentChildrenRequest(
                1L, 1L
        );

        User parentUser = new User();
        parentUser.setId(invitationCodeResponse.userId());
        parentUser.setName("John");
        parentUser.setLastNames("Doe");
        parentUser.setEmail("johndoe@gmail.com");
        parentUser.setUserType(UserType.PARENT);

        UserResponse parentResponse = new UserResponse(
                parentUser.getId(),
                parentUser.getName(),
                parentUser.getLastNames(),
                "uploads/profilePics/default.jpg",
                parentUser.getUserType(),
                parentUser.getEmail(),
                false,
                987654321,
                null
        );
        Parent parentEntity = new Parent();
        parentEntity.setUserId(parentUser.getId());
        parentEntity.setUser(parentUser);
        parentEntity.setPhone(987654321);

        when(userRepository.existsByEmail("janedoe@gmail.com")).thenReturn(false);
        when(userRepository.save(user)).thenReturn(user);
        when(childrenRepository.save(children)).thenReturn(children);
        when(parentRepository.findById(parentUser.getId())).thenReturn(Optional.of(parentEntity));

        when(userMapper.toUserEntity(parentRequest)).thenReturn(newUser);
        when(userMapper.toChildEntity(parentRequest, newUser, user)).thenReturn(children);
        when(userMapper.toUserResponse(user, null, children)).thenReturn(expectedResponse);
        when(userMapper.toUserResponse(eq(parentUser), any(), any())).thenReturn(parentResponse);

        when(passwordEncoder.encode("P#ssword123")).thenReturn("encodedPassword");

        when(walletService.createWallet(new CreateWalletRequest(WalletType.STANDARD, user.getId())))
                .thenReturn(new WalletResponse(1, "W-123456", WalletType.STANDARD, BigDecimal.ZERO, 1L));
        when(invitationCodesService.isInvitationCodeValid("invitationCode123")).thenReturn(true);
        when(invitationCodesService.getInvitationCodeByCode("invitationCode123")).thenReturn(invitationCodeResponse);
        doNothing().when(invitationCodesService).updateInvitationCode("invitationCode123");
        when(userRepository.findById(invitationCodeResponse.userId())).thenReturn(Optional.of(parentUser));

        when(parentChildrenService.createParentChildren(createParentChildrenRequest)).
                thenReturn(new ParentChildrenResponse(1L,1L, RelationshipType.PARENT));


        // Act
        UserResponse response = userService.registerChild(parentRequest);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.id());
        assertEquals("Jane", response.name());
        assertEquals("Doe", response.lastNames());
        assertEquals("janedoe@gmail.com", response.email());
        assertEquals(UserType.CHILD, response.userType());
        assertEquals(15, response.age());

        verify(userRepository, times(1)).existsByEmail("janedoe@gmail.com");
        verify(userRepository, times(1)).save(user);
        verify(childrenRepository, times(1)).save(children);
        verify(userAuthRespository, times(1)).save(newUser);
        verify(walletService, times(1)).createWallet(new CreateWalletRequest(WalletType.STANDARD, user.getId()));
        verify(invitationCodesService, times(1)).isInvitationCodeValid("invitationCode123");
        verify(invitationCodesService, times(2)).getInvitationCodeByCode("invitationCode123");
        verify(invitationCodesService, times(1)).updateInvitationCode("invitationCode123");

    }

    @Test
    @DisplayName("CP06 - Registrar un hijo con código de invitación inválido")
    void createChildren_invalidInvitationCode_shouldThrowException() {
        // Arrange
        CreateChildrenRequest request = new CreateChildrenRequest(
                "janedoe@gmail.com",
                "P#ssword123",
                "invalidInvitationCode");
        when(invitationCodesService.isInvitationCodeValid("invalidInvitationCode")).thenReturn(false);
        // Act & Assert
        assertThrows(InvalidInvitationCodeException.class, () -> userService.registerChild(request));
    }

    @Test
    @DisplayName ("CP07 - Registrar un hijo con formato de correo electrónico inválido")
    void createChildren_invalidEmailFormat_shouldThrowException() {
        // Arrange
        CreateChildrenRequest request = new CreateChildrenRequest(
                "invalid-email-format",
                "P#ssword123",
                "invitationCode123"
        );
        // Act & Assert
        assertThrows(InvalidEmailFormatException.class, () -> userService.registerChild(request));
    }
    @Test
    @DisplayName("CP08 - Registrar un hijo con email ya existente")
    void createChildren_emailAlreadyExists_shouldThrowException() {
        // Arrange
        CreateChildrenRequest request = new CreateChildrenRequest(
                "janedoe@gmail.com",
                "P#ssword123",
                "invitationCode123"
        );
        when(userRepository.existsByEmail("janedoe@gmail.com")).thenReturn(true);
        // Act & Assert
        assertThrows(EmailAlreadyExistsException.class, () -> userService.registerChild(request));
    }
    @Test
    @DisplayName("CP09 - Registrar un hijo con contraseña inválida")
    void createChildren_invalidPasswordFormat_shouldThrowException() {
        // Arrange
        CreateChildrenRequest request = new CreateChildrenRequest(
                "janedoe@gmail.com",
                "short",
                "invitationCode123");
        // Act & Assert
        assertThrows(InvalidPasswordFormatException.class, () -> userService.registerChild(request));
    }
    @Test
    @DisplayName("CP13.1 - Enviar código de verificación a usuario válido")
    void sendVerificationCode_validUser_shouldSendCode() {
        // Arrange
        Long userId = 1L;
        CreateAuthCodeRequest request = new CreateAuthCodeRequest(userId, AuthCodeType.EMAIL_VERIFICATION);
        AuthCodeResponse expectedResponse = new AuthCodeResponse(
                1L, userId, "123456", AuthCodeType.EMAIL_VERIFICATION, false, LocalDateTime.now().plusMinutes(5)
        );

        when(authCodeService.createAuthCode(request)).thenReturn(expectedResponse);

        // Act
        AuthCodeResponse response = authCodeService.createAuthCode(request);

        // Assert
        assertNotNull(response);
        assertEquals(userId, response.userId());
        assertEquals("123456", response.code());
        assertFalse(response.isUsed());
        verify(authCodeService, times(1)).createAuthCode(request);
    }
    @Test
    @DisplayName("CP13.2 - Validar código correcto no expirado")
    void validateCode_validCode_shouldMarkAsUsed() {
        // Arrange
        Long userId = 1L;
        String code = "123456";
        when(authCodeService.validateAuthCode(userId, code)).thenReturn(true);

        // Act
        boolean isValid = authCodeService.validateAuthCode(userId, code);

        // Assert
        assertTrue(isValid);
        verify(authCodeService, times(1)).validateAuthCode(userId, code);
    }
    @Test
    @DisplayName("CP13.3 - Validar código expirado")
    void validateCode_expiredCode_shouldThrowException() {
        // Arrange
        Long userId = 1L;
        String code = "111111";
        when(authCodeService.validateAuthCode(userId, code)).thenThrow(CodeExpiredException.class);

        // Act & Assert
        assertThrows(CodeExpiredException.class, () -> authCodeService.validateAuthCode(userId, code));
    }
    @Test
    @DisplayName("CP13.4 - Validar código incorrecto")
    void validateCode_invalidCode_shouldThrowException() {
        // Arrange
        Long userId = 1L;
        String code = "000000";
        when(authCodeService.validateAuthCode(userId, code)).thenThrow(CodeNotFoundException.class);

        // Act & Assert
        assertThrows(CodeNotFoundException.class, () -> authCodeService.validateAuthCode(userId, code));
    }

        //pasword


    @Nested
    @DisplayName("H11 - Cambio de contraseña")
    class ChangePasswordTests {

        @Test
        @DisplayName("CP 11.1 Escenario exitoso: el usuario cambia su contraseña correctamente")
        void testChangePasswordSuccessfully() {
            Long userId = 1L;
            String oldPassword = "OldPassword123!";
            String newPassword = "NewPassword123!";
            String encodedNewPassword = "encodedNewPassword";

            UserAuth userAuth = new UserAuth();
            userAuth.setId(userId);
            userAuth.setPassword("encodedOldPassword");

            ChangePasswordRequest request = new ChangePasswordRequest(userId, oldPassword, newPassword);

            when(userAuthRespository.findById(userId)).thenReturn(Optional.of(userAuth));
            when(passwordEncoder.matches(oldPassword, userAuth.getPassword())).thenReturn(true);
            when(passwordEncoder.encode(newPassword)).thenReturn(encodedNewPassword);

            // MOCK ESTÁTICO DEL VALIDADOR
            try (MockedStatic<EmailPasswordValidator> mockedValidator = mockStatic(EmailPasswordValidator.class)) {
                mockedValidator.when(() -> EmailPasswordValidator.isValidPassword(newPassword)).thenReturn(true);

                userService.changePassword(request);

                assertEquals(encodedNewPassword, userAuth.getPassword());
                verify(userAuthRespository).save(userAuth);
            }
        }

        @Test
        @DisplayName("CP 11.2 Falla: el usuario no existe")
        void testChangePasswordUserNotFound() {
            // Arrange
            Long userId = 2L;
            ChangePasswordRequest request = new ChangePasswordRequest(userId, "any", "any");

            when(userAuthRespository.findById(userId)).thenReturn(Optional.empty());

            // Act & Assert
            assertThrows(IllegalArgumentException.class, () -> userService.changePassword(request));
        }

        @Test
        @DisplayName("CP 11.3 Falla: la contraseña actual es incorrecta")
        void testChangePasswordIncorrectCurrentPassword() {
            // Arrange
            Long userId = 3L;
            String oldPassword = "WrongPassword";
            UserAuth userAuth = new UserAuth();
            userAuth.setId(userId);
            userAuth.setPassword("encodedOldPassword");

            ChangePasswordRequest request = new ChangePasswordRequest(userId, oldPassword, "NewPassword123!");

            when(userAuthRespository.findById(userId)).thenReturn(Optional.of(userAuth));
            when(passwordEncoder.matches(oldPassword, userAuth.getPassword())).thenReturn(false);

            // Act & Assert
            assertThrows(InvalidPasswordException.class, () -> userService.changePassword(request));
        }

        @Test
        @DisplayName("CP 11.4 Falla : la nueva contraseña tiene formato inválido")
        void testChangePasswordInvalidNewPasswordFormat() {
            // Arrange
            Long userId = 4L;
            String oldPassword = "OldPassword123!";
            String newPassword = "short";
            UserAuth userAuth = new UserAuth();
            userAuth.setId(userId);
            userAuth.setPassword("encodedOldPassword");

            ChangePasswordRequest request = new ChangePasswordRequest(userId, oldPassword, newPassword);

            when(userAuthRespository.findById(userId)).thenReturn(Optional.of(userAuth));
            when(passwordEncoder.matches(oldPassword, userAuth.getPassword())).thenReturn(true);

            // Simular fallo de validación
            mockStatic(EmailPasswordValidator.class);
            when(EmailPasswordValidator.isValidPassword(newPassword)).thenReturn(false);

            // Act & Assert
            assertThrows(InvalidPasswordFormatException.class, () -> userService.changePassword(request));
        }

        @Test
        @DisplayName("CP 11.5 Fallo por autenticación: la contraseña actual no coincide")
        void testChangePasswordFailsDueToWrongOldPassword() {
            Long userId = 1L;
            String wrongOldPassword = "WrongOldPassword123!";
            String realOldPassword = "RealOldPassword123!";
            String newPassword = "NewPassword123!";

            UserAuth userAuth = new UserAuth();
            userAuth.setId(userId);
            userAuth.setPassword("encodedRealOldPassword");

            ChangePasswordRequest request = new ChangePasswordRequest(userId, wrongOldPassword, newPassword);

            when(userAuthRespository.findById(userId)).thenReturn(Optional.of(userAuth));
            // Simular que la contraseña no coincide
            when(passwordEncoder.matches(wrongOldPassword, userAuth.getPassword())).thenReturn(false);

            InvalidPasswordException exception = assertThrows(
                    InvalidPasswordException.class,
                    () -> userService.changePassword(request)
            );

            assertEquals("La contraseña actual no es correcta.", exception.getMessage());
        }
    }

        @Nested
        @DisplayName("h12")
        class UpdateProfilePictureTest {

            @Mock private UserRepository userRepository;
            @Mock private MultipartFile file;
            @InjectMocks private UserService userService;

            @BeforeEach
            void setUp() {
                MockitoAnnotations.openMocks(this);
            }

            @Test
            @DisplayName("12.1 Escenario 1: Cambio de foto exitoso")
            void updateProfilePicture_successful() throws Exception {
                Long userId = 1L;
                String fakeFileName = "profile.jpg";
                byte[] fileBytes = "image-content".getBytes();

                User user = new User();
                user.setId(userId);
                user.setProfilePicture("uploads/profilePics/old.jpg");

                when(file.getOriginalFilename()).thenReturn(fakeFileName);
                when(file.getBytes()).thenReturn(fileBytes);
                when(file.isEmpty()).thenReturn(false);
                when(file.getContentType()).thenReturn("image/jpeg");
                when(userRepository.findById(userId)).thenReturn(Optional.of(user));

                try (MockedStatic<ImageValidator> imageValidator = Mockito.mockStatic(ImageValidator.class)) {
                    imageValidator.when(() -> ImageValidator.isValidImage(file)).thenReturn(true);

                    userService.updateProfilePicture(userId, file);

                    verify(userRepository).save(user);
                    assertNotEquals("uploads/profilePics/old.jpg", user.getProfilePicture());
                    assertTrue(user.getProfilePicture().contains("uploads/profilePics/"));
                    assertTrue(user.getProfilePicture().endsWith(fakeFileName));
                }
            }

            @Test
            @DisplayName("CP 12.2 Escenario 2: Error por imagen no válida")
            void updateProfilePicture_invalidImage() {
                Long userId = 2L;

                when(file.isEmpty()).thenReturn(true);

                try (MockedStatic<ImageValidator> imageValidator = Mockito.mockStatic(ImageValidator.class)) {
                    imageValidator.when(() -> ImageValidator.isValidImage(file)).thenReturn(false);

                    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                            userService.updateProfilePicture(userId, file)
                    );

                    assertEquals("La imagen no es válida o supera el tamaño permitido.", exception.getMessage());
                }
            }

            @Test
            @DisplayName("CP 12.3 Escenario 3: Fallo técnico durante la actualización")
            void updateProfilePicture_ioException() throws Exception {
                Long userId = 3L;

                User user = new User();
                user.setId(userId);
                user.setProfilePicture("uploads/profilePics/default.jpg");

                when(userRepository.findById(userId)).thenReturn(Optional.of(user));
                when(file.getOriginalFilename()).thenReturn("fail.jpg");
                when(file.getBytes()).thenThrow(new IOException("Simulated I/O error"));

                try (MockedStatic<ImageValidator> imageValidator = Mockito.mockStatic(ImageValidator.class)) {
                    imageValidator.when(() -> ImageValidator.isValidImage(file)).thenReturn(true);

                    RuntimeException exception = assertThrows(RuntimeException.class, () ->
                            userService.updateProfilePicture(userId, file)
                    );

                    assertTrue(exception.getMessage().contains("Error al guardar la imagen"));
                }
            }
        }


}



