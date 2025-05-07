package com.keepu.webAPI.repository;

import com.keepu.webAPI.model.Badges;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BadgesRepository extends JpaRepository<Badges, Integer> {
}