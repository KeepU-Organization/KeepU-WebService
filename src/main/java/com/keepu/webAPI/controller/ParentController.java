package com.keepu.webAPI.controller;

import com.keepu.webAPI.dto.request.CreateParentRequest;
import com.keepu.webAPI.dto.response.ParentResponse;
import com.keepu.webAPI.service.ParentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/parents")
@RequiredArgsConstructor
public class ParentController {

    private final ParentService parentService;

    @PostMapping("/users/{userId}/parents")
    public ResponseEntity<ParentResponse> createParent(
            @PathVariable Integer userId,
            @RequestBody CreateParentRequest request) {

        ParentResponse response = parentService.createParent(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}