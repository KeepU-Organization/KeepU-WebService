package com.keepu.webAPI.service;

import com.keepu.webAPI.dto.request.CreateParentRequest;
import com.keepu.webAPI.dto.response.ParentResponse;
import com.keepu.webAPI.exception.NotFoundException;
import com.keepu.webAPI.mapper.ParentMapper;
import com.keepu.webAPI.model.Parent;
import com.keepu.webAPI.model.User;
import com.keepu.webAPI.repository.ParentRepository;
import com.keepu.webAPI.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ParentService {

    private final ParentRepository parentRepository;
    private final UserRepository userRepository;
    private final ParentMapper parentMapper;

    @Transactional
    public ParentResponse createParent(Integer userId, CreateParentRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        Parent parent = parentMapper.toParentEntity(request, user);
        Parent savedParent = parentRepository.save(parent);

        return parentMapper.toParentResponse(savedParent);
    }

}