package com.keepu.webAPI.service;

import com.keepu.webAPI.dto.request.*;
import com.keepu.webAPI.dto.response.*;
import com.keepu.webAPI.exception.EmailAlreadyExistsException;
import com.keepu.webAPI.exception.InvalidEmailFormatException;
import com.keepu.webAPI.exception.InvalidInvitationCodeException;
import com.keepu.webAPI.exception.InvalidPasswordFormatException;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

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
}



