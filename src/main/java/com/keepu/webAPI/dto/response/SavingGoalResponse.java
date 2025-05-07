package com.keepu.webAPI.dto.response;

public record SavingGoalResponse(
        Integer id,
        String name,
        Double targetAmount,
        Double currentAmount,
        boolean isCompleted,
        Integer userId
) {}