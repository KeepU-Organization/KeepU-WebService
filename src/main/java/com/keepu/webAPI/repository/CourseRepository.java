package com.keepu.webAPI.repository;

import com.keepu.webAPI.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Integer> {
    Optional<Course> findByCode(String code);
}