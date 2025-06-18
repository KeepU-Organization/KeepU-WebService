package com.keepu.webAPI.dto.response;

public record ModuleResponse(
        Integer id,
        String title,
        String description,
        Integer orderIndex,
        String courseCode,
        String imageUrl,
        Integer duration,
        String code
) {}