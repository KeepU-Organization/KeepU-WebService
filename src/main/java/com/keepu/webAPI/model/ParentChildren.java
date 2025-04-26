package com.keepu.webAPI.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Parent_children")
public class ParentChildren {

    @EmbeddedId
    private ParentChildrenId id;

    @ManyToOne
    @MapsId("id_parent")
    @JoinColumn(name = "id_parent")
    private Parent parent;

    @ManyToOne
    @MapsId("id_children")
    @JoinColumn(name = "id_children")
    private Children children;

    private String relationship_type;
}
