package com.keepu.webAPI.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "parent_children")
public class ParentChildren {

    @EmbeddedId
    private ParentChildrenId id;

    @ManyToOne
    @MapsId("parentId")
    @JoinColumn(name = "parent_id", nullable = false)
    private Parent parent;

    @ManyToOne
    @MapsId("childId")
    @JoinColumn(name = "child_id", nullable = false)
    private Children child;

    @Column(name = "relationship_type")
    private String relationshipType;
}