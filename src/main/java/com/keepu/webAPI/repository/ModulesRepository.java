package com.keepu.webAPI.repository;

import com.keepu.webAPI.model.Modules;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ModulesRepository extends JpaRepository<Modules, Integer> {
    List<Modules> findByCourseId(Integer courseId);

    Optional<Modules> findByCode(String code);
}