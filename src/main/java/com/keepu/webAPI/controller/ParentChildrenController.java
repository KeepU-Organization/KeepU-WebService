package com.keepu.webAPI.controller;

import com.keepu.webAPI.dto.request.CreateParentChildrenRequest;
import com.keepu.webAPI.dto.response.ParentChildrenResponse;
import com.keepu.webAPI.dto.response.UserResponse;
import com.keepu.webAPI.service.ParentChildrenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/parent-children")
@RequiredArgsConstructor
public class ParentChildrenController {

    private final ParentChildrenService parentChildrenService;

    @PostMapping
    public ResponseEntity<ParentChildrenResponse> create(@Valid @RequestBody CreateParentChildrenRequest request) {
        return ResponseEntity.ok(parentChildrenService.createParentChildren(request));
    }
    @GetMapping("/{parentId}")
    public ResponseEntity<List<UserResponse.ChildSummary>> getChildrenSummaryByParentId(@PathVariable Long parentId) {
        return ResponseEntity.ok(parentChildrenService.getChildrenSummaryByParentId(parentId));
    }
}