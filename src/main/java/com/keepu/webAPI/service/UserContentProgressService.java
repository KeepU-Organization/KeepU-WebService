package com.keepu.webAPI.service;

import com.keepu.webAPI.dto.request.CreateUserContentProgressRequest;
import com.keepu.webAPI.dto.response.UserContentProgressResponse;
import com.keepu.webAPI.exception.NotFoundException;
import com.keepu.webAPI.mapper.UserContentProgressMapper;
import com.keepu.webAPI.model.ContentItems;
import com.keepu.webAPI.model.User;
import com.keepu.webAPI.model.UserContentProgress;
import com.keepu.webAPI.repository.ContentItemsRepository;
import com.keepu.webAPI.repository.UserContentProgressRepository;
import com.keepu.webAPI.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserContentProgressService {

    private final UserContentProgressRepository userContentProgressRepository;
    private final UserRepository userRepository;
    private final ContentItemsRepository contentItemsRepository;
    private final UserContentProgressMapper userContentProgressMapper;

    @Transactional
    public UserContentProgressResponse createUserContentProgress(CreateUserContentProgressRequest request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        ContentItems content = contentItemsRepository.findById(request.contentId())
                .orElseThrow(() -> new NotFoundException("Contenido no encontrado"));

        UserContentProgress progress = userContentProgressMapper.toUserContentProgressEntity(request, user, content);
        UserContentProgress savedProgress = userContentProgressRepository.save(progress);

        return userContentProgressMapper.toUserContentProgressResponse(savedProgress);
    }

    @Transactional(readOnly = true)
    public UserContentProgressResponse getUserContentProgressById(Integer id) {
        UserContentProgress progress = userContentProgressRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Progreso de contenido no encontrado"));
        return userContentProgressMapper.toUserContentProgressResponse(progress);
    }
}