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

import java.util.List;

@Service
@RequiredArgsConstructor
public class ModulesService {

    private final ModulesRepository modulesRepository;
    private final CourseRepository courseRepository;
    private final ModulesMapper modulesMapper;

    @Transactional
    public ModuleResponse createModule(CreateModuleRequest request) {
        Course course = courseRepository.findByCode(request.courseCode())
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
    @Transactional (readOnly = true)
    public List<ModuleResponse> getModulesByCourseId(Integer courseId) {
        List<Modules> modules = modulesRepository.findByCourseId(courseId);
        if (modules.isEmpty()) {
            throw new NotFoundException("No se encontraron módulos para el curso con ID: " + courseId);
        }
        return modulesMapper.toModuleResponseList(modules);
    }
    @Transactional (readOnly = true)
    public List<ModuleResponse> getModulesByCourseCode(String courseCode) {
        Course course = courseRepository.findByCode(courseCode)
                .orElseThrow(() -> new NotFoundException("Curso no encontrado con código: " + courseCode));
        List<Modules> modules = modulesRepository.findByCourseId(course.getId());
        if (modules.isEmpty()) {
            throw new NotFoundException("No se encontraron módulos para el curso con código: " + courseCode);
        }
        return modulesMapper.toModuleResponseList(modules);
    }
}