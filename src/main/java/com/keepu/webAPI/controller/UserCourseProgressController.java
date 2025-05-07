package com.keepu.webAPI.controller;

import com.keepu.webAPI.dto.request.CreateUserCourseProgressRequest;
import com.keepu.webAPI.dto.response.UserCourseProgressResponse;
import com.keepu.webAPI.service.UserCourseProgressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user-course-progress")
@RequiredArgsConstructor
public class UserCourseProgressController {

    private final UserCourseProgressService userCourseProgressService;

    @PostMapping
    public ResponseEntity<UserCourseProgressResponse> create(@Valid @RequestBody CreateUserCourseProgressRequest request) {
        return ResponseEntity.ok(userCourseProgressService.createUserCourseProgress(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserCourseProgressResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(userCourseProgressService.getUserCourseProgressById(id));
    }
}