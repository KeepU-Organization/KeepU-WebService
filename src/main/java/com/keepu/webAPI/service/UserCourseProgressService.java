package com.keepu.webAPI.service;

import com.keepu.webAPI.dto.request.CreateUserCourseProgressRequest;
import com.keepu.webAPI.dto.response.UserCourseProgressResponse;
import com.keepu.webAPI.exception.NotFoundException;
import com.keepu.webAPI.mapper.UserCourseProgressMapper;
import com.keepu.webAPI.model.Course;
import com.keepu.webAPI.model.User;
import com.keepu.webAPI.model.UserCourseProgress;
import com.keepu.webAPI.repository.CourseRepository;
import com.keepu.webAPI.repository.UserCourseProgressRepository;
import com.keepu.webAPI.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserCourseProgressService {

    private final UserCourseProgressRepository userCourseProgressRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final UserCourseProgressMapper userCourseProgressMapper;

    @Transactional
    public UserCourseProgressResponse createUserCourseProgress(CreateUserCourseProgressRequest request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        Course course = courseRepository.findById(request.courseId())
                .orElseThrow(() -> new NotFoundException("Curso no encontrado"));

        UserCourseProgress progress = userCourseProgressMapper.toUserCourseProgressEntity(request, user, course);
        UserCourseProgress savedProgress = userCourseProgressRepository.save(progress);

        return userCourseProgressMapper.toUserCourseProgressResponse(savedProgress);
    }

    @Transactional(readOnly = true)
    public UserCourseProgressResponse getUserCourseProgressById(Integer id) {
        UserCourseProgress progress = userCourseProgressRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Progreso del curso no encontrado"));
        return userCourseProgressMapper.toUserCourseProgressResponse(progress);
    }
}