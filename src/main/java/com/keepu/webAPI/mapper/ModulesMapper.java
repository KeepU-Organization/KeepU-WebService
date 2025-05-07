package com.keepu.webAPI.mapper;

import com.keepu.webAPI.dto.request.CreateModuleRequest;
import com.keepu.webAPI.dto.response.ModuleResponse;
import com.keepu.webAPI.model.Modules;
import com.keepu.webAPI.model.Course;
import org.springframework.stereotype.Component;

@Component
public class ModulesMapper {

    public ModuleResponse toModuleResponse(Modules module) {
        if (module == null) {
            return null;
        }
        return new ModuleResponse(
                module.getId(),
                module.getTitle(),
                module.getDescription(),
                module.getOrderIndex(),
                module.getCourse().getId()
        );
    }

    public Modules toModuleEntity(CreateModuleRequest request, Course course) {
        if (request == null || course == null) {
            return null;
        }

        Modules module = new Modules();
        module.setTitle(request.title());
        module.setDescription(request.description());
        module.setOrderIndex(request.orderIndex());
        module.setCourse(course);
        return module;
    }
}