package com.keepu.webAPI.controller;

import com.keepu.webAPI.dto.request.CreateChildrenRequest;
import com.keepu.webAPI.dto.request.CreateParentRequest;
import com.keepu.webAPI.dto.request.CreateUserRequest;
import com.keepu.webAPI.dto.response.UserResponse;
import com.keepu.webAPI.exception.UserNotFoundException;
import com.keepu.webAPI.dto.request.ChangePasswordRequest;
import com.keepu.webAPI.model.Parent;
import com.keepu.webAPI.model.User;
import com.keepu.webAPI.model.UserAuth;
import com.keepu.webAPI.model.enums.UserType;
import com.keepu.webAPI.repository.ChildrenRepository;
import com.keepu.webAPI.repository.ParentRepository;
import com.keepu.webAPI.repository.UserAuthRespository;
import com.keepu.webAPI.repository.UserRepository;
import com.keepu.webAPI.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Tag(name="User Management", description = "Operations related to user management")
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final ParentRepository parentRepository;
    private final ChildrenRepository childrenRepository;
    private final UserAuthRespository userAuthRespository;



    @Operation(summary = "Register a new user"
    , description = "Registers a new user with the provided details. The user type is PARENT." +
            "The response contains id, name, last names, user type, email, and profile picture URL.",
    tags = {"parent","post"})
    @ApiResponses({
            @ApiResponse(responseCode = "201", content={@Content(schema=@Schema(implementation = UserResponse.class),mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", content= {@Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", content= {@Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500",content= {@Content(schema = @Schema())
    }) })
    @PostMapping("/register/parent")
    public ResponseEntity<UserResponse> registerParent(@RequestBody CreateParentRequest request) {
        UserResponse response = userService.registerParent(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Register a new user",
            description = "Registers a new user with the provided details. The user type is CHILD." +
                    "The response contains id, name, last names, user type, email, and profile picture URL.",
            tags = {"child", "post"})
    @ApiResponses({
            @ApiResponse(responseCode = "201", content={@Content(schema=@Schema(implementation = UserResponse.class),mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", content= {@Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", content= {@Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500",content= {@Content(schema = @Schema())
            }) })

    @PostMapping("/register/child")
    public ResponseEntity<UserResponse> registerChild(@RequestBody CreateChildrenRequest request) {
        UserResponse response = userService.registerChild(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation (summary="Retrieve a user by ID",
            description = "Retrieves a user by their ID. The response contains user details such as id, name, last names, user type, email, and profile picture URL.",
            tags = { "get"})
    @ApiResponses({
            @ApiResponse(responseCode = "201", content={@Content(schema=@Schema(implementation = UserResponse.class),mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", content= {@Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", content= {@Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500",content= {@Content(schema = @Schema())
            }) })
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long userId) {
        UserResponse response = userService.getUserById(userId);
        return ResponseEntity.ok(response);
    }

    @Operation (summary="Retrive the current authenticated user",
            description = "Retrieves the current authenticated user. The response contains user details such as id, name, last names, user type, email, profile picture URL, phone number, and age.",
            tags = {"get", "me"})
    @ApiResponses({
            @ApiResponse(responseCode = "201", content={@Content(schema=@Schema(implementation = UserResponse.class),mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", content= {@Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", content= {@Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500",content= {@Content(schema = @Schema())
            }) })
    @GetMapping("/me")
    public ResponseEntity<UserResponse.MeResponse> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        // Verificar si userDetails es nulo
        if (userDetails == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario no autenticado");
        }

        UserAuth userAuth = userAuthRespository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        int phoneNumber = 999999999;
        int age = 99;
        if (userAuth .getUser() .getUserType() == UserType.PARENT) {
            phoneNumber = parentRepository.findByUserId(userAuth.getUser() .getId()).getPhone();
        }
        else if (userAuth .getUser() .getUserType() == UserType.CHILD) {
            age = childrenRepository.findByUserId(userAuth .getUser() .getId()).getAge();
        }

        UserResponse.MeResponse response = new UserResponse.MeResponse(
                userAuth .getUser().getId(), userAuth .getUser().getName(),  userAuth .getUser().getLastNames(),
                userAuth .getUser().getUserType(), userAuth .getUser().getEmail(),
                userAuth.isHas2FA(), userAuth .isEmailVerified(),
                userAuth .getUser().isDarkMode(),phoneNumber,age, userAuth .getUser().getProfilePicture());

        return ResponseEntity.ok(response);
    }

    @Operation (summary="Retrive all users",
            description = "Retrieves all users in the system. The response contains a list of user details such as id, name, last names, user type, email, and profile picture URL.",
            tags = {"get", "all"})
    @ApiResponses({
            @ApiResponse(responseCode = "201", content={@Content(schema=@Schema(implementation = UserResponse.class),mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", content= {@Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", content= {@Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500",content= {@Content(schema = @Schema())
            }) })

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserAuth>> getAllUsersAuth() {
        return ResponseEntity.ok(userService.getAllUsersAuth());

    }

    @Operation (summary="Update profile picture",
            description = "Updates the profile picture of a user. The request should contain the user ID and the new profile picture file.",
            tags = {"put"})
    @ApiResponses({
            @ApiResponse(responseCode = "201", content={@Content(schema=@Schema(implementation = UserResponse.class),mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", content= {@Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", content= {@Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500",content= {@Content(schema = @Schema())
            }) })
    // Actualizar la foto de perfil
    @PostMapping("/{userId}/profile-picture")
    public ResponseEntity<?> updateProfilePicture(
            @PathVariable Long userId,
            @RequestParam("file") MultipartFile file) {
        try {
            userService.updateProfilePicture(userId, file);
            return ResponseEntity.ok("Foto de perfil actualizada con éxito.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error técnico: " + e.getMessage());
        }
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest request) {
        userService.changePassword(request);
        return ResponseEntity.ok("Contraseña actualizada correctamente.");
    }
    @PutMapping("/{userId}/security-key")
    public ResponseEntity<String> changeSecurityKey(
            @PathVariable Long userId,
            @RequestParam String newSecurityKey) {
        try {
            userService.changeSecurityKey(userId, newSecurityKey);
            return ResponseEntity.ok("Clave de seguridad actualizada correctamente.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error técnico: " + e.getMessage());
        }
    }
    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok("Usuario eliminado correctamente.");
    }

}