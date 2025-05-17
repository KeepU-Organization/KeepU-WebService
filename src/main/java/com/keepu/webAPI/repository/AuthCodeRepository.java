package com.keepu.webAPI.repository;

import com.keepu.webAPI.model.AuthCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthCodeRepository extends JpaRepository<AuthCode, Integer> {
    Optional<AuthCode> findByCode(String code);
}