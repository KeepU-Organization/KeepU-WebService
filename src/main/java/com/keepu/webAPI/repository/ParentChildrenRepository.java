package com.keepu.webAPI.repository;

import com.keepu.webAPI.dto.response.UserResponse;
import com.keepu.webAPI.model.ParentChildren;
import com.keepu.webAPI.model.ParentChildrenId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ParentChildrenRepository extends JpaRepository<ParentChildren, ParentChildrenId> {

    @Query("SELECT ua.child.userId FROM ParentChildren ua WHERE ua.parent.userId = :parentId")
    List<Number>findChildrenByParent(Long parentId);
}