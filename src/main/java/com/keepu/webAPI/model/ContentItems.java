package com.keepu.webAPI.model;
import com.keepu.webAPI.model.enums.ContentType;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "content_items")
public class ContentItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 200)
    private String description;

    @Column(nullable = false)
    private int orderIndex;

    @Column(nullable = true, length = 255)
    private String url; // Optional URL for the content item, can be null if not applicable

    @Lob
    @Column(name = "content_data", nullable = true, columnDefinition = "TEXT")
    private String contentData; // Optional content data, can be null if not applicable

    @ManyToOne(optional = false)
    @JoinColumn(name = "module_id", nullable = false)
    private Modules module;

    @Enumerated(EnumType.STRING)
    @Column(name = "content_type", nullable = false)
    private ContentType contentType;

    @Column(name = "imageUrl", nullable = true)
    private String imageUrl; // Optional image URL for the content item

    @Column(name = "duration", nullable = true)
    private Integer duration; // Duration in minutes, nullable if not applicable

    @Column(name="code", length = 50, unique = true)
    private String code; // Unique code for the content item, can be used for identification or linking
}