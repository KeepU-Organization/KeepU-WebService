package com.keepu.webAPI.model;

import com.keepu.webAPI.model.enums.StoreType;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "stores")
public class Stores {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String location;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    @Column(name = "type", nullable = false)
    private StoreType type; // e.g., "gaming","clothes", etc..

    @Column(name="link", nullable = false)
    private String link; // e.g., "https://example.com/store"
}