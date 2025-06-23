


package com.keepu.webAPI.service;

import com.keepu.webAPI.dto.request.CreateQuizQuestionRequest;
import com.keepu.webAPI.dto.response.QuizQuestionResponse;
import com.keepu.webAPI.mapper.QuizQuestionMapper;
import com.keepu.webAPI.model.ContentItems;
import com.keepu.webAPI.model.QuizQuestion;
import com.keepu.webAPI.repository.ContentItemsRepository;
import com.keepu.webAPI.repository.QuizQuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizQuestionService {
    private final QuizQuestionRepository quizQuestionRepository;
    private final ContentItemsRepository contentItemsRepository;
    private final QuizQuestionMapper quizQuestionMapper;
    @Transactional(readOnly = true)
    public QuizQuestion findById(Long id) {
        return quizQuestionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quiz question not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public  QuizQuestion findByContentId(Integer contentId) {
        ContentItems contentItems = contentItemsRepository.findById(contentId)
                .orElseThrow(() -> new RuntimeException("Content item not found with id: " + contentId));
        return quizQuestionRepository.findByContentItem(contentItems)
                .orElseThrow(() -> new RuntimeException("Quiz question not found for content item with id: " + contentId));
    }
    @Transactional
    public QuizQuestionResponse createQuizQuestion(CreateQuizQuestionRequest createQuizQuestionRequest) {
        ContentItems contentItems = contentItemsRepository.findByCode((createQuizQuestionRequest.contentItemCode()))
                .orElseThrow(() -> new RuntimeException("Content item not found with id: " + createQuizQuestionRequest.contentItemCode()));

        QuizQuestion quizQuestion= quizQuestionMapper.toQuizQuestionEntity(createQuizQuestionRequest, contentItems);
        quizQuestionRepository.save(quizQuestion);
        return quizQuestionMapper.toQuizQuestionResponse(quizQuestion);
    }
    @Transactional(readOnly = true)
    public QuizQuestionResponse getQuizQuestionsByContentItemCode(String contentItemCode) {
        ContentItems contentItems = contentItemsRepository.findByCode(contentItemCode)
                .orElseThrow(() -> new RuntimeException("Content item not found with code: " + contentItemCode));
        QuizQuestion quizQuestions = quizQuestionRepository.findByContentItem(contentItems).orElseThrow(()
        -> new RuntimeException("Quiz question not found for content item with code: " + contentItemCode));
        return quizQuestionMapper.toQuizQuestionResponse(quizQuestions);
    }
}
