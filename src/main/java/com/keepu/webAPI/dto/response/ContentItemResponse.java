package com.keepu.webAPI.dto.response;

public record ContentItemResponse(
        Integer id,
        String title,
        String description,
        Integer orderIndex,
        String url,
        String contentData, // Optional content data, can be null if not applicable
        String contentType,
        String moduleCode,
        String imageUrl,
        Integer duration, // Duration in minutes, nullable if not applicable
        String code // Unique code for the content item, can be used for identification or linking
) {}