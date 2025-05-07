package com.keepu.webAPI.service;

import com.keepu.webAPI.dto.request.CreateCourseRequest;
import com.keepu.webAPI.dto.response.CourseResponse;
import com.keepu.webAPI.exception.NotFoundException;
import com.keepu.webAPI.mapper.CourseMapper;
import com.keepu.webAPI.model.Course;
import com.keepu.webAPI.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    @Transactional
    public CourseResponse createCourse(CreateCourseRequest request) {
        Course course = courseMapper.toCourseEntity(request);
        Course savedCourse = courseRepository.save(course);
        return courseMapper.toCourseResponse(savedCourse);
    }

    @Transactional(readOnly = true)
    public CourseResponse getCourseById(Integer id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Curso no encontrado"));
        return courseMapper.toCourseResponse(course);
    }
}