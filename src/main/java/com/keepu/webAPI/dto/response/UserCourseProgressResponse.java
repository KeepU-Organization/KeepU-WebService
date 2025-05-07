package com.keepu.webAPI.dto.response;

public record UserCourseProgressResponse(
        Integer id,
        Integer userId,
        Integer courseId,
        Double progressPercentage,
        Boolean isCompleted
) {}