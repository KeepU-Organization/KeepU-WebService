package com.keepu.webAPI.repository;

import com.keepu.webAPI.model.ContentItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentItemsRepository extends JpaRepository<ContentItems, Integer> {
}