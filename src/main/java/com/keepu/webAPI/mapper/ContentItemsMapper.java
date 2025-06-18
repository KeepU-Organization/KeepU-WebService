package com.keepu.webAPI.mapper;

import com.keepu.webAPI.dto.request.CreateContentItemRequest;
import com.keepu.webAPI.dto.response.ContentItemResponse;
import com.keepu.webAPI.model.enums.ContentType;
import com.keepu.webAPI.model.ContentItems;
import com.keepu.webAPI.model.Modules;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ContentItemsMapper {

    public ContentItemResponse toContentItemResponse(ContentItems contentItem) {
        if (contentItem == null) {
            return null;
        }
        return new ContentItemResponse(
                contentItem.getId(),
                contentItem.getTitle(),
                contentItem.getDescription(),
                contentItem.getOrderIndex(),
                contentItem.getUrl(),
                contentItem.getContentData(), // Optional content data, can be null if not applicable
                contentItem.getContentType().name(),
                contentItem.getModule().getCode(),
                contentItem.getImageUrl(),
                contentItem.getDuration() // Duration in minutes, nullable if not applicable
                , contentItem.getCode() // Unique code for the content item
        );
    }

    public ContentItems toContentItemEntity(CreateContentItemRequest request, Modules module) {
        if (request == null || module == null) {
            return null;
        }

        ContentItems contentItem = new ContentItems();
        contentItem.setTitle(request.title());
        contentItem.setDescription(request.description());
        contentItem.setOrderIndex(request.orderIndex());
        contentItem.setUrl(request.url());
        contentItem.setContentData(request.contentData());
        contentItem.setContentType(request.contentType());
        contentItem.setModule(module);
        contentItem.setImageUrl(request.imageUrl());
        contentItem.setDuration(request.duration());
        contentItem.setCode(request.code());
        return contentItem;
    }

    public List<ContentItemResponse> toContentItemResponseList(List<ContentItems> contentItemsList) {
        if (contentItemsList == null || contentItemsList.isEmpty()) {
            return List.of();
        }
        return contentItemsList.stream()
                .map(this::toContentItemResponse)
                .toList();
    }
}