package com.keepu.webAPI.service;

import com.keepu.webAPI.model.ParentChildren;
import com.keepu.webAPI.model.ParentChildrenId;
import com.keepu.webAPI.repository.ParentChildrenRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ParentChildrenService {

    private final ParentChildrenRepository repo;

    public ParentChildrenService(ParentChildrenRepository repo) {
        this.repo = repo;
    }

    public List<ParentChildren> findAll() {
        return repo.findAll();
    }

    public ParentChildren findById(ParentChildrenId id) {
        return repo.findById(id).orElse(null);
    }

    public ParentChildren save(ParentChildren pc) {
        return repo.save(pc);
    }

    public void deleteById(ParentChildrenId id) {
        repo.deleteById(id);
    }
}
