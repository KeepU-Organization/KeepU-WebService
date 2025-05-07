package com.keepu.webAPI.repository;

import com.keepu.webAPI.model.ParentChildren;
import com.keepu.webAPI.model.ParentChildrenId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParentChildrenRepository extends JpaRepository<ParentChildren, ParentChildrenId> {
}