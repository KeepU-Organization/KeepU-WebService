package com.keepu.webAPI.repository;

import com.keepu.webAPI.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
}