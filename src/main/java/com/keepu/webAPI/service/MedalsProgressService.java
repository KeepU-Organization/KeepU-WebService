// MedalsProgressService.java
package com.keepu.webAPI.service;


import com.keepu.webAPI.model.MedalProgress;

import com.keepu.webAPI.dto.response.MedalsProgressResponse;
import com.keepu.webAPI.dto.request.CreateMedalsProgressRequest;
import com.keepu.webAPI.mapper.MedalProgressMapper;
import com.keepu.webAPI.mapper.NewsMapper;
import com.keepu.webAPI.repository.MedalProgressRepository;
import com.keepu.webAPI.repository.NewsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedalsProgressService {

    private final MedalProgressRepository medalProgressRepository;
    private final NewsRepository newsRepository;
    private final MedalProgressMapper medalProgressMapper;
    private final NewsMapper newsMapper;

    public MedalsProgressService(
            MedalProgressRepository medalProgressRepository,
            NewsRepository newsRepository,
            MedalProgressMapper medalProgressMapper,
            NewsMapper newsMapper
    ) {
        this.medalProgressRepository = medalProgressRepository;
        this.newsRepository = newsRepository;
        this.medalProgressMapper = medalProgressMapper;
        this.newsMapper = newsMapper;
    }

    public MedalsProgressResponse getMedalsProgress(CreateMedalsProgressRequest request) {
        var medals = medalProgressRepository.findByUserId(request.userId())
                .stream()
                .map(medalProgressMapper::mapToResponse)
                .toList();

        var news = newsRepository.findByUserId(request.userId())
                .stream()
                .map(newsMapper::mapToResponse)
                .toList();

        return new MedalsProgressResponse(medals, news);
    }
}


