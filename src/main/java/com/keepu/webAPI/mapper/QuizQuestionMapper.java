package com.keepu.webAPI.mapper;

import com.keepu.webAPI.dto.request.CreateQuizQuestionRequest;
import com.keepu.webAPI.dto.response.QuizOptionResponse;
import com.keepu.webAPI.dto.response.QuizQuestionResponse;
import com.keepu.webAPI.model.ContentItems;
import com.keepu.webAPI.model.QuizQuestion;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class QuizQuestionMapper {
    public QuizQuestionResponse toQuizQuestionResponse(QuizQuestion quizQuestion) {
        if (quizQuestion == null) {
            return null;
        }

        return new QuizQuestionResponse(
                quizQuestion.getId().intValue(),
                quizQuestion.getQuestionText(),
                quizQuestion.getContentItem().getCode(),
                quizQuestion.getOptions().stream()
                        .map(option -> new QuizOptionResponse(
                                option.getId().intValue(),
                                option.getText(),
                                option.isCorrect(),
                                option.getQuestion().getCode()))
                        .toArray(QuizOptionResponse[]::new),
                quizQuestion.getCode()
        );
    }
    public QuizQuestion toQuizQuestionEntity(CreateQuizQuestionRequest createQuizQuestionRequest,
                                             ContentItems contentItem) {
        if (createQuizQuestionRequest == null) {
            return null;
        }

        QuizQuestion quizQuestion = new QuizQuestion();
        quizQuestion.setQuestionText(createQuizQuestionRequest.questionText());
        quizQuestion.setContentItem(contentItem);
        quizQuestion.setOptions(Arrays.asList(createQuizQuestionRequest.options()));
        quizQuestion.setCode(createQuizQuestionRequest.code());
        return quizQuestion;
    }

}
