package com.keepu.webAPI.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "quiz_options")
@NoArgsConstructor
@AllArgsConstructor
public class QuizOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String text;

    @Column(nullable = false)
    private boolean isCorrect;

    @ManyToOne(optional = false)
    @JoinColumn(name = "question_id")
    private QuizQuestion question;
}
