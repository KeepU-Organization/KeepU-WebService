package com.keepu.webAPI.dto.response;

public record QuizQuestionResponse(
        Integer id, // The unique identifier for the quiz question
        String questionText, // The text of the quiz question
        String contentItemCode, // The ID of the content item this question belongs to
        QuizOptionResponse[] options // An array of quiz options associated with the question
        , String code // The unique identifier for the quiz question
) {
}
