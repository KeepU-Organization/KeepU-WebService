package com.keepu.webAPI.repository;

import com.keepu.webAPI.model.SpendingLimits;
import com.keepu.webAPI.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpendingLimitsRepository extends JpaRepository<SpendingLimits, Integer> {
    Optional<SpendingLimits> findByWallet(Wallet wallet);
}