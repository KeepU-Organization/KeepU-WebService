package com.keepu.webAPI.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class ParentChildrenId implements Serializable {

    private Integer parentId;
    private Integer childId;
}