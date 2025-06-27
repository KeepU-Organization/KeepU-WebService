package com.keepu.webAPI.repository;

import com.keepu.webAPI.model.User;
import com.keepu.webAPI.model.UserContentProgress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserContentProgressRepository extends JpaRepository<UserContentProgress, Integer> {
    Optional<UserContentProgress>findByUser(User user);
}