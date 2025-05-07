package com.keepu.webAPI.repository;

import com.keepu.webAPI.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Integer> {
}