package com.keepu.webAPI.repository;

import com.keepu.webAPI.model.Stores;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoresRepository extends JpaRepository<Stores, Integer> {
}