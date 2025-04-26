package com.keepu.webAPI.service;

import com.keepu.webAPI.model.Parent;
import com.keepu.webAPI.repository.ParentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParentService {

    private final ParentRepository parentRepository;

    public ParentService(ParentRepository parentRepository) {
        this.parentRepository = parentRepository;
    }

    public List<Parent> findAll() {
        return parentRepository.findAll();
    }

    public Parent findById(Integer id) {
        return parentRepository.findById(id).orElse(null);
    }

    public Parent save(Parent parent) {
        return parentRepository.save(parent);
    }

    public void deleteById(Integer id) {
        parentRepository.deleteById(id);
    }
}
