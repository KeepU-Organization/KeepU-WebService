package com.keepu.webAPI.mapper;

import com.keepu.webAPI.dto.request.CreateUserCourseProgressRequest;
import com.keepu.webAPI.dto.response.UserCourseProgressResponse;
import com.keepu.webAPI.model.Course;
import com.keepu.webAPI.model.User;
import com.keepu.webAPI.model.UserCourseProgress;
import org.springframework.stereotype.Component;

@Component
public class UserCourseProgressMapper {

    public UserCourseProgressResponse toUserCourseProgressResponse(UserCourseProgress progress) {
        if (progress == null) {
            return null;
        }
        return new UserCourseProgressResponse(
                progress.getId(),
                progress.getUser().getId(),
                progress.getCourse().getId(),
                progress.getProgressPercentage(),
                progress.isCompleted()
        );
    }

    public UserCourseProgress toUserCourseProgressEntity(CreateUserCourseProgressRequest request, User user, Course course) {
        if (request == null || user == null || course == null) {
            return null;
        }

        UserCourseProgress progress = new UserCourseProgress();
        progress.setUser(user);
        progress.setCourse(course);
        progress.setProgressPercentage(request.progressPercentage());
        progress.setCompleted(request.isCompleted());
        return progress;
    }
}