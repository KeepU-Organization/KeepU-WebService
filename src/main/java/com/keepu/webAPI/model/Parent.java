package com.keepu.webAPI.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Parent")
public class Parent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_parent;

    private Integer phone;

    @OneToOne
    @JoinColumn(name = "id_parent")
    private User user;
}
