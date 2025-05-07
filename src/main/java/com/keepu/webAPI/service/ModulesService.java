package com.keepu.webAPI.service;

import com.keepu.webAPI.dto.request.CreateModuleRequest;
import com.keepu.webAPI.dto.response.ModuleResponse;
import com.keepu.webAPI.exception.NotFoundException;
import com.keepu.webAPI.mapper.ModulesMapper;
import com.keepu.webAPI.model.Modules;
import com.keepu.webAPI.model.Course;
import com.keepu.webAPI.repository.ModulesRepository;
import com.keepu.webAPI.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ModulesService {

    private final ModulesRepository modulesRepository;
    private final CourseRepository courseRepository;
    private final ModulesMapper modulesMapper;

    @Transactional
    public ModuleResponse createModule(CreateModuleRequest request) {
        Course course = courseRepository.findById(request.courseId())
                .orElseThrow(() -> new NotFoundException("Curso no encontrado"));

        Modules module = modulesMapper.toModuleEntity(request, course);
        Modules savedModule = modulesRepository.save(module);

        return modulesMapper.toModuleResponse(savedModule);
    }

    @Transactional(readOnly = true)
    public ModuleResponse getModuleById(Integer id) {
        Modules module = modulesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Módulo no encontrado"));
        return modulesMapper.toModuleResponse(module);
    }
}