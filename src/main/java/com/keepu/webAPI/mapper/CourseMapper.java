package com.keepu.webAPI.mapper;

import com.keepu.webAPI.dto.request.CreateCourseRequest;
import com.keepu.webAPI.dto.response.CourseResponse;
import com.keepu.webAPI.model.Course;
import org.springframework.stereotype.Component;

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
                course.isPremium()
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
        return course;
    }
}