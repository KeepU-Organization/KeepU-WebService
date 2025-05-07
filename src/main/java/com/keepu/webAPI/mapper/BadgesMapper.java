package com.keepu.webAPI.mapper;

import com.keepu.webAPI.dto.request.CreateBadgeRequest;
import com.keepu.webAPI.dto.response.BadgeResponse;
import com.keepu.webAPI.model.Badges;
import org.springframework.stereotype.Component;

@Component
public class BadgesMapper {

    public BadgeResponse toBadgeResponse(Badges badge) {
        if (badge == null) {
            return null;
        }
        return new BadgeResponse(
                badge.getId(),
                badge.getName(),
                badge.getDescription(),
                badge.getImageUrl(),
                badge.getPointsCost()
        );
    }

    public Badges toBadgeEntity(CreateBadgeRequest request) {
        if (request == null) {
            return null;
        }

        Badges badge = new Badges();
        badge.setName(request.name());
        badge.setDescription(request.description());
        badge.setImageUrl(request.imageUrl());
        badge.setPointsCost(request.pointsCost());
        return badge;
    }
}