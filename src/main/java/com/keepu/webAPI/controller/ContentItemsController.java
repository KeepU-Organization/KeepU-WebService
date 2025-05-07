package com.keepu.webAPI.controller;

import com.keepu.webAPI.dto.request.CreateContentItemRequest;
import com.keepu.webAPI.dto.response.ContentItemResponse;
import com.keepu.webAPI.service.ContentItemsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/content-items")
@RequiredArgsConstructor
public class ContentItemsController {

    private final ContentItemsService contentItemsService;

    @PostMapping
    public ResponseEntity<ContentItemResponse> create(@Valid @RequestBody CreateContentItemRequest request) {
        return ResponseEntity.ok(contentItemsService.createContentItem(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContentItemResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(contentItemsService.getContentItemById(id));
    }
}