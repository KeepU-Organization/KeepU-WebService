package com.keepu.webAPI.mapper;

import com.keepu.webAPI.dto.request.CreateContentItemRequest;
import com.keepu.webAPI.dto.response.ContentItemResponse;
import com.keepu.webAPI.model.enums.ContentType;
import com.keepu.webAPI.model.ContentItems;
import com.keepu.webAPI.model.Modules;
import org.springframework.stereotype.Component;

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
                contentItem.getContentType().name(),
                contentItem.getModule().getId()
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
        contentItem.setContentType(ContentType.valueOf(request.contentType()));
        contentItem.setModule(module);
        return contentItem;
    }
}