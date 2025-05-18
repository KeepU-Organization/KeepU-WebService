package com.keepu.webAPI.controller;

import com.keepu.webAPI.dto.request.CreateChildrenRequest;
import com.keepu.webAPI.dto.request.CreateParentRequest;
import com.keepu.webAPI.dto.request.CreateUserRequest;
import com.keepu.webAPI.dto.response.UserResponse;
import com.keepu.webAPI.exception.UserNotFoundException;
import com.keepu.webAPI.model.Parent;
import com.keepu.webAPI.model.User;
import com.keepu.webAPI.model.enums.UserType;
import com.keepu.webAPI.repository.ChildrenRepository;
import com.keepu.webAPI.repository.ParentRepository;
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
    public ResponseEntity<UserResponse> getUser(@PathVariable Integer userId) {
        UserResponse response = userService.getUserById(userId);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        // Verificar si userDetails es nulo
        if (userDetails == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario no autenticado");
        }

        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        boolean isParent = false;
        boolean isChild = false;
        int phoneNumber = 999999999;
        int age = 99;
        if (user.getUserType() == UserType.PARENT) {
            isParent = true;
            phoneNumber = parentRepository.findByUserId(user.getId()).getPhone();
        }
        else if (user.getUserType() == UserType.CHILD) {
            isChild = true;
            age = childrenRepository.findByUserId(user.getId()).getAge();
        }

        UserResponse response = new UserResponse(user.getId(), user.getName(),  user.getLastNames(),user.getUserType(), user.getEmail(), user.isHas2FA(),user.isAuthenticated(),user.isActive(),user.isDarkMode(),user.getCreatedAt(),isParent,isChild,phoneNumber,age, user.getProfilePicture());

        return ResponseEntity.ok(response);
    }
    // Actualizar la foto de perfil
    @PostMapping("/{userId}/profile-picture")
    public ResponseEntity<?> updateProfilePicture(
            @PathVariable Integer userId,
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