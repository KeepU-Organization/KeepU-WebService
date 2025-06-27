package com.keepu.webAPI.dto.response;

public record QuizOptionResponse(
        Integer id, // The unique identifier for the quiz option
        String text, // The text of the quiz option
        boolean isCorrect, // Indicates if this option is the correct answer
        String questionCode // The ID of the quiz question this option belongs to
) {
}
