package com.keepu.webAPI.repository;

import com.keepu.webAPI.model.Children;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChildrenRepository extends JpaRepository<Children, Long> {
    Children findByUserId(Long userId);
}