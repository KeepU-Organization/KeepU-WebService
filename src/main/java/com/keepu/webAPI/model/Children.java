package com.keepu.webAPI.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "children")
public class Children {

    @Id
    private Long userId;  // Campo explícito para la clave primaria

    @OneToOne
    @JoinColumn(name = "child_id", nullable = false)
    @MapsId
    private User user;


    @Column(nullable = false)
    private Integer age;
}