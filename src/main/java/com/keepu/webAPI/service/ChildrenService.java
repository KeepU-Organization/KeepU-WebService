package com.keepu.webAPI.service;

import com.keepu.webAPI.model.Children;
import com.keepu.webAPI.repository.ChildrenRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChildrenService {

    private final ChildrenRepository childrenRepository;

    public ChildrenService(ChildrenRepository childrenRepository) {
        this.childrenRepository = childrenRepository;
    }

    public List<Children> findAll() {
        return childrenRepository.findAll();
    }

    public Children findById(Integer id) {
        return childrenRepository.findById(id).orElse(null);
    }

    public Children save(Children child) {
        return childrenRepository.save(child);
    }

    public void deleteById(Integer id) {
        childrenRepository.deleteById(id);
    }
}
