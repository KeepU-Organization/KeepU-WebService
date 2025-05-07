package com.keepu.webAPI.repository;

import com.keepu.webAPI.model.UserBadges;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBadgesRepository extends JpaRepository<UserBadges, Integer> {
}