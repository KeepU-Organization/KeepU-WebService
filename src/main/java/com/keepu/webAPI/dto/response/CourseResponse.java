package com.keepu.webAPI.dto.response;

public record CourseResponse(
        Integer id,
        String title,
        String description,
        Integer difficultyLevel,
        Boolean isPremium,
        String imageUrl,
        String code
) {}