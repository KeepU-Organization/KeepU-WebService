package com.keepu.webAPI.service;

import com.keepu.webAPI.dto.request.CreateUserBadgeRequest;
import com.keepu.webAPI.dto.response.UserBadgeResponse;
import com.keepu.webAPI.exception.NotFoundException;
import com.keepu.webAPI.mapper.UserBadgesMapper;
import com.keepu.webAPI.model.Badges;
import com.keepu.webAPI.model.User;
import com.keepu.webAPI.model.UserBadges;
import com.keepu.webAPI.repository.BadgesRepository;
import com.keepu.webAPI.repository.UserBadgesRepository;
import com.keepu.webAPI.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserBadgesService {

    private final UserBadgesRepository userBadgesRepository;
    private final UserRepository userRepository;
    private final BadgesRepository badgesRepository;
    private final UserBadgesMapper userBadgesMapper;

    @Transactional
    public UserBadgeResponse createUserBadge(CreateUserBadgeRequest request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        Badges badge = badgesRepository.findById(request.badgeId())
                .orElseThrow(() -> new NotFoundException("Insignia no encontrada"));

        UserBadges userBadge = userBadgesMapper.toUserBadgeEntity(request, user, badge);
        UserBadges savedUserBadge = userBadgesRepository.save(userBadge);

        return userBadgesMapper.toUserBadgeResponse(savedUserBadge);
    }

    @Transactional(readOnly = true)
    public UserBadgeResponse getUserBadgeById(Integer id) {
        UserBadges userBadge = userBadgesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Insignia de usuario no encontrada"));
        return userBadgesMapper.toUserBadgeResponse(userBadge);
    }
}