package com.keepu.webAPI.service;

import com.keepu.webAPI.dto.request.CreateContentItemRequest;
import com.keepu.webAPI.dto.response.ContentItemResponse;
import com.keepu.webAPI.exception.NotFoundException;
import com.keepu.webAPI.mapper.ContentItemsMapper;
import com.keepu.webAPI.model.ContentItems;
import com.keepu.webAPI.model.Modules;
import com.keepu.webAPI.repository.ContentItemsRepository;
import com.keepu.webAPI.repository.ModulesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ContentItemsService {

    private final ContentItemsRepository contentItemsRepository;
    private final ModulesRepository modulesRepository;
    private final ContentItemsMapper contentItemsMapper;

    @Transactional
    public ContentItemResponse createContentItem(CreateContentItemRequest request) {
        Modules module = modulesRepository.findById(request.moduleId())
                .orElseThrow(() -> new NotFoundException("Módulo no encontrado"));

        ContentItems contentItem = contentItemsMapper.toContentItemEntity(request, module);
        ContentItems savedContentItem = contentItemsRepository.save(contentItem);

        return contentItemsMapper.toContentItemResponse(savedContentItem);
    }

    @Transactional(readOnly = true)
    public ContentItemResponse getContentItemById(Integer id) {
        ContentItems contentItem = contentItemsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Elemento de contenido no encontrado"));
        return contentItemsMapper.toContentItemResponse(contentItem);
    }
}