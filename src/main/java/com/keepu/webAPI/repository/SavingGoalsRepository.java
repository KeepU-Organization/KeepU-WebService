package com.keepu.webAPI.repository;

import com.keepu.webAPI.model.SavingGoals;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavingGoalsRepository extends JpaRepository<SavingGoals, Integer> {
}