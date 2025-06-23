package com.keepu.webAPI.controller;

import com.keepu.webAPI.dto.response.QuizOptionResponse;
import com.keepu.webAPI.service.QuizOptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/quiz-options")
@RequiredArgsConstructor

public class QuizOptionController {
    private final QuizOptionService quizOptionService;
    @GetMapping("/question/{questionCode}")
    public List<QuizOptionResponse> getQuizOptionsByQuestionCode(@PathVariable String questionCode) {

        return quizOptionService.getQuizOptionByQuestionCode(questionCode);
    }
}
