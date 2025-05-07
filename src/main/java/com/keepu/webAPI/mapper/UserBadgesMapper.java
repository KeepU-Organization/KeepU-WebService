package com.keepu.webAPI.mapper;

import com.keepu.webAPI.dto.request.CreateUserBadgeRequest;
import com.keepu.webAPI.dto.response.UserBadgeResponse;
import com.keepu.webAPI.model.Badges;
import com.keepu.webAPI.model.User;
import com.keepu.webAPI.model.UserBadges;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class UserBadgesMapper {

    public UserBadgeResponse toUserBadgeResponse(UserBadges userBadge) {
        if (userBadge == null) {
            return null;
        }
        return new UserBadgeResponse(
                userBadge.getId(),
                userBadge.getUser().getId(),
                userBadge.getBadge().getId(),
                userBadge.getEarnedAt()
        );
    }

    public UserBadges toUserBadgeEntity(CreateUserBadgeRequest request, User user, Badges badge) {
        if (request == null || user == null || badge == null) {
            return null;
        }

        UserBadges userBadge = new UserBadges();
        userBadge.setUser(user);
        userBadge.setBadge(badge);
        userBadge.setEarnedAt(LocalDateTime.now());
        return userBadge;
    }
}