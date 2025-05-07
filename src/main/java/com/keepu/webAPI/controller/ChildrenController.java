package com.keepu.webAPI.controller;

import com.keepu.webAPI.dto.request.CreateChildrenRequest;
import com.keepu.webAPI.dto.response.ChildrenResponse;
import com.keepu.webAPI.service.ChildrenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/children")
@RequiredArgsConstructor
public class ChildrenController {

    private final ChildrenService childrenService;

    @PostMapping
    public ResponseEntity<ChildrenResponse> create(@Valid @RequestBody CreateChildrenRequest request) {
        return ResponseEntity.ok(childrenService.createChildren(request));
    }
}