package com.keepu.webAPI.repository;

import com.keepu.webAPI.model.QuizOption;
import com.keepu.webAPI.model.QuizQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuizOptionRespository extends JpaRepository<QuizOption, Long> {

    Optional<List<QuizOption>> findByQuestion(QuizQuestion question);
}
