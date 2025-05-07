package com.keepu.webAPI.repository;

import com.keepu.webAPI.model.AuthCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthCodeRepository extends JpaRepository<AuthCode, Integer> {
}