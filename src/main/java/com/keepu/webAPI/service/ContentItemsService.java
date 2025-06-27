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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContentItemsService {

    private final ContentItemsRepository contentItemsRepository;
    private final ModulesRepository modulesRepository;
    private final ContentItemsMapper contentItemsMapper;

    @Transactional
    public ContentItemResponse createContentItem(CreateContentItemRequest request) {


        Modules module = modulesRepository.findByCode((request.moduleCode()))
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
    @Transactional(readOnly = true)
    public List<ContentItemResponse> getAllContentItemsByModuleId(Integer moduleId) {
        Modules module = modulesRepository.findById(moduleId)
                .orElseThrow(() -> new NotFoundException("Módulo no encontrado"));

        List<ContentItems> contentItemsList = contentItemsRepository.findByModule(module);
        return contentItemsMapper.toContentItemResponseList(contentItemsList);
    }
    @Transactional(readOnly = true)
    public List<ContentItemResponse>getAllContentItemsByModuleCode(String moduleCode) {
        Modules module = modulesRepository.findByCode(moduleCode)
                .orElseThrow(() -> new NotFoundException("Módulo no encontrado"));

        List<ContentItems> contentItemsList = contentItemsRepository.findByModule(module);
        return contentItemsMapper.toContentItemResponseList(contentItemsList);
    }
}