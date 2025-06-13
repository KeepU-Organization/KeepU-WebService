package com.keepu.webAPI.repository;

import com.keepu.webAPI.model.User;
import com.keepu.webAPI.model.UserBadges;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserBadgesRepository extends JpaRepository<UserBadges, Integer> {
    Optional<UserBadges> findByUser(User user);
}