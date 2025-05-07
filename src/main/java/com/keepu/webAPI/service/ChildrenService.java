package com.keepu.webAPI.service;

import com.keepu.webAPI.dto.request.CreateChildrenRequest;
import com.keepu.webAPI.dto.response.ChildrenResponse;
import com.keepu.webAPI.exception.NotFoundException;
import com.keepu.webAPI.mapper.ChildrenMapper;
import com.keepu.webAPI.model.Children;
import com.keepu.webAPI.model.User;
import com.keepu.webAPI.repository.ChildrenRepository;
import com.keepu.webAPI.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChildrenService {

    private final ChildrenRepository childrenRepository;
    private final UserRepository userRepository;
    private final ChildrenMapper childrenMapper;

    @Transactional
    public ChildrenResponse createChildren(CreateChildrenRequest request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new NotFoundException("User not found"));

        Children children = childrenMapper.toChildrenEntity(request, user);
        Children savedChildren = childrenRepository.save(children);

        return childrenMapper.toChildrenResponse(savedChildren);
    }
}