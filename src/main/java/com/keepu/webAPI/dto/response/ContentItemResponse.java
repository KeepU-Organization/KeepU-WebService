package com.keepu.webAPI.dto.response;

public record ContentItemResponse(
        Integer id,
        String title,
        String description,
        Integer orderIndex,
        String url,
        String contentType,
        Integer moduleId
) {}