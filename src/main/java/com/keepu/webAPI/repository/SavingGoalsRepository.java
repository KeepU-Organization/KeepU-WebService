package com.keepu.webAPI.repository;

import com.keepu.webAPI.model.SavingGoals;
import com.keepu.webAPI.model.User;
import com.keepu.webAPI.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SavingGoalsRepository extends JpaRepository<SavingGoals, Integer> {
    Optional<SavingGoals> findByUser(User user);

    User user(User user);
}