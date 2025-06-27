package com.keepu.webAPI.repository;

import com.keepu.webAPI.model.ContentItems;
import com.keepu.webAPI.model.Modules;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContentItemsRepository extends JpaRepository<ContentItems, Integer> {
    List<ContentItems> findByModule(Modules module);

    Optional <ContentItems> findByCode(String code);
}