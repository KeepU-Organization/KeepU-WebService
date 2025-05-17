package com.keepu.webAPI.service;

import com.keepu.webAPI.model.EducationalModule;
import com.keepu.webAPI.repository.EducationalModuleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EducationalModuleService {
    private final EducationalModuleRepository repository;

    public EducationalModule addModule(EducationalModule module) {
        return repository.save(module);
    }

    public EducationalModuleService(EducationalModuleRepository repository) {
        this.repository = repository;
    }

    public List<EducationalModule> getAllModules() {
        return repository.findAll();
    }

    public Optional<EducationalModule> getModuleById(Integer id) {
        return repository.findById(id);
    }
}
