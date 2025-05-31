package com.keepu.webAPI.dto.response;

public record UserContentProgressResponse(
        Integer id,
        Long userId,
        Integer contentId,
        Double progressPercentage
) {}