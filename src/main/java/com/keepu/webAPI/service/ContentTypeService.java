package com.keepu.webAPI.service;

import com.keepu.webAPI.model.ContentType;
import com.keepu.webAPI.repository.ContentTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContentTypeService {

    private final ContentTypeRepository repo;

    public ContentTypeService(ContentTypeRepository repo) {
        this.repo = repo;
    }

    public List<ContentType> findAll() {
        return repo.findAll();
    }

    public ContentType findById(Integer id) {
        return repo.findById(id).orElse(null);
    }

    public ContentType save(ContentType content) {
        return repo.save(content);
    }

    public void deleteById(Integer id) {
        repo.deleteById(id);
    }
}
