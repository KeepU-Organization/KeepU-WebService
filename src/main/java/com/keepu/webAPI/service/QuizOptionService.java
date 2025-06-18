package com.keepu.webAPI.service;

import com.keepu.webAPI.dto.request.CreateQuizOptionRequest;
import com.keepu.webAPI.dto.response.QuizOptionResponse;
import com.keepu.webAPI.mapper.QuizOptionMapper;
import com.keepu.webAPI.model.QuizOption;
import com.keepu.webAPI.model.QuizQuestion;
import com.keepu.webAPI.repository.QuizOptionRespository;
import com.keepu.webAPI.repository.QuizQuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizOptionService {
    private final QuizQuestionRepository quizQuestionRepository;
    private final QuizOptionMapper quizOptionMapper;
    private final QuizOptionRespository quizOptionRespository;
    @Transactional
    public QuizOptionResponse createQuizOption(CreateQuizOptionRequest createQuizOptionRequest) {

        QuizQuestion quizQuestion = quizQuestionRepository.findByCode(createQuizOptionRequest.questionCode())
                .orElseThrow(() -> new RuntimeException("Quiz question not found with code: " + createQuizOptionRequest.questionCode()));
        QuizOption quizOption = quizOptionMapper.toQuizOptionEntity(createQuizOptionRequest, quizQuestion);
        quizOption = quizOptionRespository.save(quizOption);
        return quizOptionMapper.toQuizOptionResponse(quizOption);
    }
    @Transactional(readOnly = true)
    public List< QuizOptionResponse >getQuizOptionByQuestionCode(String code) {
        QuizQuestion quizQuestion = quizQuestionRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Quiz question not found with code: " + code));
        List <QuizOption> quizOption = quizOptionRespository.findByQuestion(quizQuestion)
                .orElseThrow(() -> new RuntimeException("Quiz option not found for question with code: " + code));
        return quizOptionMapper. toQuizOptionResponseList(quizOption);
    }
}
