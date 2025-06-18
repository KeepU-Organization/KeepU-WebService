package com.keepu.webAPI.dto.request;

import com.keepu.webAPI.model.QuizOption;

public record CreateQuizQuestionRequest(
        String questionText, // The text of the quiz question
        String contentItemCode, // The ID of the content item this question belongs to
        QuizOption [] options // An array of quiz options associated with the question
        , String code // The unique identifier for the quiz question
) {
}
