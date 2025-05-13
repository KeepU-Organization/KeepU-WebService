package com.keepu.webAPI.repository;

import com.keepu.webAPI.model.User;
import com.keepu.webAPI.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Integer> {
    Optional<Wallet>  findByUser(User user);
}