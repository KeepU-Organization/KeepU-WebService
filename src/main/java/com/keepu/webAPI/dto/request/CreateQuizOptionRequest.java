package com.keepu.webAPI.dto.request;

public record CreateQuizOptionRequest (
        String text,
        boolean isCorrect,
        String questionCode // The ID of the quiz question this option belongs to
){
}
