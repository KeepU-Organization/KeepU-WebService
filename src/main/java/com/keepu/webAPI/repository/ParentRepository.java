package com.keepu.webAPI.repository;

import com.keepu.webAPI.model.Parent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParentRepository extends JpaRepository<Parent, Long> {
    Parent findByUserId(Long userId);

    void deleteByUserId(Long userId);
}