package com.keepu.webAPI.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Financial_content")
public class FinancialContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_content;

    @ManyToOne
    @JoinColumn(name = "id_content_type")
    private ContentType contentType;

    private String title;
    private String description;
    private String url;
}
