package com.keepu.webAPI.service;

import com.keepu.webAPI.model.FinancialContent;
import com.keepu.webAPI.repository.FinancialContentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FinancialContentService {

    private final FinancialContentRepository repo;

    public FinancialContentService(FinancialContentRepository repo) {
        this.repo = repo;
    }

    public List<FinancialContent> findAll() {
        return repo.findAll();
    }

    public FinancialContent findById(Integer id) {
        return repo.findById(id).orElse(null);
    }

    public FinancialContent save(FinancialContent content) {
        return repo.save(content);
    }

    public void deleteById(Integer id) {
        repo.deleteById(id);
    }
}
