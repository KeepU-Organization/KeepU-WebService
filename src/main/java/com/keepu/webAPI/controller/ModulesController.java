package com.keepu.webAPI.controller;

import com.keepu.webAPI.dto.request.CreateModuleRequest;
import com.keepu.webAPI.dto.response.ModuleResponse;
import com.keepu.webAPI.service.ModulesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/modules")
@RequiredArgsConstructor
public class ModulesController {

    private final ModulesService modulesService;

    @PostMapping
    public ResponseEntity<ModuleResponse> create(@Valid @RequestBody CreateModuleRequest request) {
        return ResponseEntity.ok(modulesService.createModule(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ModuleResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(modulesService.getModuleById(id));
    }
    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<ModuleResponse>> getByCourseId(@PathVariable Integer courseId) {
        return ResponseEntity.ok(modulesService.getModulesByCourseId(courseId));
    }
    @GetMapping("/course/code/{courseCode}")
    public ResponseEntity<List<ModuleResponse>> getByCourseCode(@PathVariable String courseCode) {
        return ResponseEntity.ok(modulesService.getModulesByCourseCode(courseCode));
    }
}