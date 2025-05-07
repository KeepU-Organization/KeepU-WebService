package com.keepu.webAPI.controller;

import com.keepu.webAPI.dto.request.CreateSavingGoalRequest;
import com.keepu.webAPI.dto.response.SavingGoalResponse;
import com.keepu.webAPI.service.SavingGoalsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/saving-goals")
@RequiredArgsConstructor
public class SavingGoalsController {

    private final SavingGoalsService savingGoalsService;

    @PostMapping
    public ResponseEntity<SavingGoalResponse> create(@Valid @RequestBody CreateSavingGoalRequest request) {
        return ResponseEntity.ok(savingGoalsService.createSavingGoal(request));
    }
}