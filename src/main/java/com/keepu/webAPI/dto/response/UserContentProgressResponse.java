package com.keepu.webAPI.dto.response;

public record UserContentProgressResponse(
        Integer id,
        Integer userId,
        Integer contentId,
        Double progressPercentage
) {}