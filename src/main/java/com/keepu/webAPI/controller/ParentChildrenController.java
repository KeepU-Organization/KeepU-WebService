package com.keepu.webAPI.controller;

import com.keepu.webAPI.dto.request.CreateParentChildrenRequest;
import com.keepu.webAPI.dto.response.ParentChildrenResponse;
import com.keepu.webAPI.service.ParentChildrenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/parent-children")
@RequiredArgsConstructor
public class ParentChildrenController {

    private final ParentChildrenService parentChildrenService;

    @PostMapping
    public ResponseEntity<ParentChildrenResponse> create(@Valid @RequestBody CreateParentChildrenRequest request) {
        return ResponseEntity.ok(parentChildrenService.createParentChildren(request));
    }
}