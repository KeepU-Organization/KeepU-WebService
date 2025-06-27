package com.keepu.webAPI.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false, length = 200)
    private String description;

    @Column(name = "difficulty_level", nullable = false)
    private int difficultyLevel;

    @Column(name = "is_premium", nullable = false)
    private boolean isPremium;
    @Column(name="image_url", length = 255)
    private String imageUrl;

    @Column(name="code", length = 50, unique = true)
    private String code;
}