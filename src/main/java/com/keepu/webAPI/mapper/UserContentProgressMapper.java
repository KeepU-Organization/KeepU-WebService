package com.keepu.webAPI.mapper;

import com.keepu.webAPI.dto.request.CreateUserContentProgressRequest;
import com.keepu.webAPI.dto.response.UserContentProgressResponse;
import com.keepu.webAPI.model.ContentItems;
import com.keepu.webAPI.model.User;
import com.keepu.webAPI.model.UserContentProgress;
import org.springframework.stereotype.Component;

@Component
public class UserContentProgressMapper {

    public UserContentProgressResponse toUserContentProgressResponse(UserContentProgress progress) {
        if (progress == null) {
            return null;
        }
        return new UserContentProgressResponse(
                progress.getId(),
                progress.getUser().getId(),
                progress.getContent().getId(),
                progress.getProgressPercentage()
        );
    }

    public UserContentProgress toUserContentProgressEntity(CreateUserContentProgressRequest request, User user, ContentItems content) {
        if (request == null || user == null || content == null) {
            return null;
        }

        UserContentProgress progress = new UserContentProgress();
        progress.setUser(user);
        progress.setContent(content);
        progress.setProgressPercentage(request.progressPercentage());
        return progress;
    }
}