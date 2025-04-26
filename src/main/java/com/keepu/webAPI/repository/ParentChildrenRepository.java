package com.keepu.webAPI.repository;

import com.keepu.webAPI.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParentChildrenRepository extends JpaRepository<ParentChildren, ParentChildrenId> {}