package com.keepu.webAPI.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "User_content_progress")
public class UserContentProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_progress;

    @ManyToOne
    @JoinColumn(name = "id_content")
    private FinancialContent content;

    @ManyToOne
    @JoinColumn(name = "id_children")
    private Children children;

    private Boolean completed;
    private LocalDate date;
}
