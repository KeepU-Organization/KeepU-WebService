package com.keepu.webAPI.repository;

import com.keepu.webAPI.model.UserCourseProgress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCourseProgressRepository extends JpaRepository<UserCourseProgress, Integer> {
}