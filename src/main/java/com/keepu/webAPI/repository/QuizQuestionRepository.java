package com.keepu.webAPI.repository;

import com.keepu.webAPI.model.ContentItems;
import com.keepu.webAPI.model.QuizQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface QuizQuestionRepository extends JpaRepository<QuizQuestion, Long> {
    Optional<QuizQuestion>  findByContentItem(ContentItems contentItems);

    Optional<QuizQuestion> findByCode(String code);
}
