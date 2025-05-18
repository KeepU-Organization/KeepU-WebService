// NewsMapper.java
package com.keepu.webAPI.mapper;

import com.keepu.webAPI.dto.response.NewsResponse;
import com.keepu.webAPI.model.News;
import org.springframework.stereotype.Component;

@Component
public class NewsMapper {
    public NewsResponse mapToResponse(News news) {
        return new NewsResponse(
                news.getId(),
                news.getTitle(),
                news.getContent(),
                news.getPublishedDate()
        );
    }
}
