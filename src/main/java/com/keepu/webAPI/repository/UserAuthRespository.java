package com.keepu.webAPI.repository;

import com.keepu.webAPI.model.AuthCode;
import com.keepu.webAPI.model.User;
import com.keepu.webAPI.model.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserAuthRespository extends JpaRepository<UserAuth, Long> {
    boolean existsByUser(User user);

    @Query("SELECT ua FROM UserAuth ua WHERE ua.user.email = :email")
    Optional<UserAuth> findByEmail(String email);

    @Query("SELECT ua FROM UserAuth ua WHERE ua.user.id = :userId")
    Optional<UserAuth> findByUserId(Long userId);
}
