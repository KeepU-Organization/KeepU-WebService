package com.keepu.webAPI.service;

import com.keepu.webAPI.dto.request.CreateParentChildrenRequest;
import com.keepu.webAPI.dto.response.ParentChildrenResponse;
import com.keepu.webAPI.exception.NotFoundException;
import com.keepu.webAPI.mapper.ParentChildrenMapper;
import com.keepu.webAPI.model.Children;
import com.keepu.webAPI.model.Parent;
import com.keepu.webAPI.model.ParentChildren;
import com.keepu.webAPI.repository.ChildrenRepository;
import com.keepu.webAPI.repository.ParentChildrenRepository;
import com.keepu.webAPI.repository.ParentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ParentChildrenService {

    private final ParentChildrenRepository parentChildrenRepository;
    private final ParentRepository parentRepository;
    private final ChildrenRepository childrenRepository;
    private final ParentChildrenMapper parentChildrenMapper;

    @Transactional
    public ParentChildrenResponse createParentChildren(CreateParentChildrenRequest request) {
        Parent parent = parentRepository.findById(request.parentId())
                .orElseThrow(() -> new NotFoundException("Parent not found"));

        Children child = childrenRepository.findById(request.childId())
                .orElseThrow(() -> new NotFoundException("Child not found"));

        ParentChildren parentChildren = parentChildrenMapper.toParentChildrenEntity(request, parent, child);
        ParentChildren savedParentChildren = parentChildrenRepository.save(parentChildren);

        return parentChildrenMapper.toParentChildrenResponse(savedParentChildren);
    }
}