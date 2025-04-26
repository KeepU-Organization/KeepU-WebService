package com.keepu.webAPI.service;

import com.keepu.webAPI.model.SpendingLimits;
import com.keepu.webAPI.repository.SpendingLimitsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpendingLimitsService {

    private final SpendingLimitsRepository repo;

    public SpendingLimitsService(SpendingLimitsRepository repo) {
        this.repo = repo;
    }

    public List<SpendingLimits> findAll() {
        return repo.findAll();
    }

    public SpendingLimits findById(Integer id) {
        return repo.findById(id).orElse(null);
    }

    public SpendingLimits save(SpendingLimits limit) {
        return repo.save(limit);
    }

    public void deleteById(Integer id) {
        repo.deleteById(id);
    }
}
