package com.keepu.webAPI.repository;

import com.keepu.webAPI.model.User;
import com.keepu.webAPI.model.UserCourseProgress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserCourseProgressRepository extends JpaRepository<UserCourseProgress, Integer> {
    Optional<UserCourseProgress>findByUser(User user);
}