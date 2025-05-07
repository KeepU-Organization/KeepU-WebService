package com.keepu.webAPI.repository;

import com.keepu.webAPI.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Integer> {
}