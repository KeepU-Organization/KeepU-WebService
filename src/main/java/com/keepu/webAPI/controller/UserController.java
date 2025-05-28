package com.keepu.webAPI.controller;

import com.keepu.webAPI.dto.request.CreateChildrenRequest;
import com.keepu.webAPI.dto.request.CreateParentRequest;
import com.keepu.webAPI.dto.request.CreateUserRequest;
import com.keepu.webAPI.dto.response.UserResponse;
import com.keepu.webAPI.exception.UserNotFoundException;
import com.keepu.webAPI.model.Parent;
import com.keepu.webAPI.model.User;
import com.keepu.webAPI.model.UserAuth;
import com.keepu.webAPI.model.enums.UserType;
import com.keepu.webAPI.repository.ChildrenRepository;
import com.keepu.webAPI.repository.ParentRepository;
import com.keepu.webAPI.repository.UserAuthRespository;
import com.keepu.webAPI.repository.UserRepository;
import com.keepu.webAPI.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final ParentRepository parentRepository;
    private final ChildrenRepository childrenRepository;
    private final UserAuthRespository userAuthRespository;

    @PostMapping("/register/parent")
    public ResponseEntity<UserResponse> registerParent(@RequestBody CreateParentRequest request) {
        UserResponse response = userService.registerParent(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/register/child")
    public ResponseEntity<UserResponse> registerChild(@RequestBody CreateChildrenRequest request) {
        UserResponse response = userService.registerChild(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long userId) {
        UserResponse response = userService.getUserById(userId);
        return ResponseEntity.ok(response);
    }
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

}