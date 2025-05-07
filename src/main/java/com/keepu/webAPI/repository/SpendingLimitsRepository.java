package com.keepu.webAPI.repository;

import com.keepu.webAPI.model.SpendingLimits;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpendingLimitsRepository extends JpaRepository<SpendingLimits, Integer> {
}