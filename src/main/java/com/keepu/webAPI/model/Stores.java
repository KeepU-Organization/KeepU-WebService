package com.keepu.webAPI.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Stores")
public class Stores {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_store;

    private String store;
}
