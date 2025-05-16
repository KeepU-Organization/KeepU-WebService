// MedalProgressMapper.java
package com.keepu.webAPI.mapper;

import com.keepu.webAPI.dto.response.MedalProgressResponse;
import com.keepu.webAPI.model.MedalProgress;
import org.springframework.stereotype.Component;

@Component
public class MedalProgressMapper {
    public MedalProgressResponse mapToResponse(MedalProgress progress) {
        return new MedalProgressResponse(
                progress.getId(),
                progress.getMedalName(),
                progress.getProgress(),
                progress.getCompleted()
        );
    }
}
