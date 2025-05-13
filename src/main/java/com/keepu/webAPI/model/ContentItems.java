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

    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false, length = 200)
    private String description;

    @Column(nullable = false)
    private int orderIndex;

    @Column(nullable = false)
    private String url;

    @ManyToOne(optional = false)
    @JoinColumn(name = "module_id", nullable = false)
    private Modules module;

    @Enumerated(EnumType.STRING)
    @Column(name = "content_type", nullable = false)
    private ContentType contentType;
}