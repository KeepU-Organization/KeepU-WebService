package com.keepu.webAPI.controller;


import com.keepu.webAPI.dto.response.QuizQuestionResponse;
import com.keepu.webAPI.model.QuizQuestion;
import com.keepu.webAPI.service.QuizQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/quiz-questions")
@RequiredArgsConstructor
public class QuizQuestionController {
    private final QuizQuestionService quizQuestionService;

    @GetMapping("/{contentId}")
    public QuizQuestion getQuizQuestionByContentId(@PathVariable Integer contentId) {
        return quizQuestionService.findByContentId(contentId);
    }
    @GetMapping("/content-item/{contentItemCode}")
    public QuizQuestionResponse getQuizQuestionByContentItemCode(@PathVariable String contentItemCode) {
        return quizQuestionService.getQuizQuestionsByContentItemCode(contentItemCode);
    }
}
