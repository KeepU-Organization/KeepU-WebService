package com.keepu.webAPI.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Children")
public class Children {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_children;

    private Integer age;

    @OneToOne
    @JoinColumn(name = "id_children")
    private User user;
}
