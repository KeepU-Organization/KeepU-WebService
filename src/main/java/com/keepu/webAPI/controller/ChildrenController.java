package com.keepu.webAPI.controller;

import com.keepu.webAPI.dto.request.CreateChildrenRequest;
import com.keepu.webAPI.dto.response.ChildrenResponse;
import com.keepu.webAPI.service.ChildrenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/children")
@RequiredArgsConstructor
public class ChildrenController {

    private final ChildrenService childrenService;

    @PostMapping("/users/{userId}/children")
    public ResponseEntity<ChildrenResponse> createChildren(
            @PathVariable Integer userId,
            @RequestBody CreateChildrenRequest request) {

        ChildrenResponse response = childrenService.createChildren(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}