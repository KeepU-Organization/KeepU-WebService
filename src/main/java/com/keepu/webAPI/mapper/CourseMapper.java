package com.keepu.webAPI.mapper;

import com.keepu.webAPI.dto.request.CreateCourseRequest;
import com.keepu.webAPI.dto.response.CourseResponse;
import com.keepu.webAPI.model.Course;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CourseMapper {

    public CourseResponse toCourseResponse(Course course) {
        if (course == null) {
            return null;
        }
        return new CourseResponse(
                course.getId(),
                course.getTitle(),
                course.getDescription(),
                course.getDifficultyLevel(),
                course.isPremium(),
                course.getImageUrl(),
                course.getCode()
        );
    }

    public Course toCourseEntity(CreateCourseRequest request) {
        if (request == null) {
            return null;
        }

        Course course = new Course();
        course.setTitle(request.title());
        course.setDescription(request.description());
        course.setDifficultyLevel(request.difficultyLevel());
        course.setPremium(request.isPremium());
        course.setImageUrl(request.imageUrl());
        course.setCode(request.code());
        return course;
    }

    public List<CourseResponse> toCourseResponseList(List<Course> courses) {
        if (courses == null || courses.isEmpty()) {
            return List.of();
        }
        return courses.stream()
                .map(this::toCourseResponse)
                .toList();
    }
}