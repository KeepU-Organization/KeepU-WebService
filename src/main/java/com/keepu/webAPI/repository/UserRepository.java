package com.keepu.webAPI.repository;

import com.keepu.webAPI.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {}