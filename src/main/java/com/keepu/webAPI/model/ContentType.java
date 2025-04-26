package com.keepu.webAPI.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Content_type")
public class ContentType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_content_type;

    private String content;
}
