package com.keepu.webAPI.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "quiz_questions")
public class QuizQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500)
    private String questionText;

    @ManyToOne(optional = false)
    @JoinColumn(name = "content_item_id")
    private ContentItems contentItem;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuizOption> options = new ArrayList<>();

    @Column(name="code", unique = true, nullable = false)
    private String code; // Unique code for the quiz question, can be used for identification or retrieval purposes.
}
