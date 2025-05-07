package com.keepu.webAPI.service;

import com.keepu.webAPI.dto.request.CreateBadgeRequest;
import com.keepu.webAPI.dto.response.BadgeResponse;
import com.keepu.webAPI.exception.NotFoundException;
import com.keepu.webAPI.mapper.BadgesMapper;
import com.keepu.webAPI.model.Badges;
import com.keepu.webAPI.repository.BadgesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BadgesService {

    private final BadgesRepository badgesRepository;
    private final BadgesMapper badgesMapper;

    @Transactional
    public BadgeResponse createBadge(CreateBadgeRequest request) {
        Badges badge = badgesMapper.toBadgeEntity(request);
        Badges savedBadge = badgesRepository.save(badge);
        return badgesMapper.toBadgeResponse(savedBadge);
    }

    @Transactional(readOnly = true)
    public BadgeResponse getBadgeById(Integer id) {
        Badges badge = badgesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Badge no encontrado"));
        return badgesMapper.toBadgeResponse(badge);
    }
}