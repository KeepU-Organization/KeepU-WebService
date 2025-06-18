package com.keepu.webAPI.mapper;

import com.keepu.webAPI.dto.request.CreateQuizOptionRequest;
import com.keepu.webAPI.dto.response.QuizOptionResponse;
import com.keepu.webAPI.model.QuizOption;
import com.keepu.webAPI.model.QuizQuestion;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QuizOptionMapper {
    public QuizOption toQuizOptionEntity(CreateQuizOptionRequest createQuizOptionRequest, QuizQuestion quizQuestion) {
        QuizOption quizOption = new QuizOption();
        quizOption.setText(createQuizOptionRequest.text());
        quizOption.setCorrect(createQuizOptionRequest.isCorrect());
        quizOption.setQuestion(quizQuestion); // Set the associated question
        // The question will be set later in the service layer
        return quizOption;
    }
    public QuizOptionResponse toQuizOptionResponse(QuizOption quizOption) {
        return new QuizOptionResponse(
                Math.toIntExact(quizOption.getId()),
                quizOption.getText(),
                quizOption.isCorrect(),
                quizOption.getQuestion().getCode() // Assuming QuizQuestion has a getCode() method
        );
    }

    public List<QuizOptionResponse> toQuizOptionResponseList(List<QuizOption> quizOption) {
        return quizOption.stream()
                .map(this::toQuizOptionResponse)
                .toList();
    }
}
