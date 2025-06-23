package com.keepu.webAPI.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "modules")
public class Modules {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false, length = 200)
    private String description;

    @Column(nullable = false)
    private int orderIndex;

    @ManyToOne(optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(name= "image_url", length = 255)
    private String imageUrl;

    @Column(name = "duration", nullable = false)
    private int duration; // Duration in minutes
    @Column(name="code", length = 50, unique = true)
    private String code;
}