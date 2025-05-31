package com.keepu.webAPI.dto.response;

public record UserCourseProgressResponse(
        Integer id,
        Long userId,
        Integer courseId,
        Double progressPercentage,
        Boolean isCompleted
) {}