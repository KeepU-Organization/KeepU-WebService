package com.keepu.webAPI.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class ParentChildrenId implements Serializable {
    private Integer id_parent;
    private Integer id_children;
}