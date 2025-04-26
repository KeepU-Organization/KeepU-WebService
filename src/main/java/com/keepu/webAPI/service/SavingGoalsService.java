package com.keepu.webAPI.service;

import com.keepu.webAPI.model.SavingGoals;
import com.keepu.webAPI.repository.SavingGoalsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SavingGoalsService {

    private final SavingGoalsRepository repo;

    public SavingGoalsService(SavingGoalsRepository repo) {
        this.repo = repo;
    }

    public List<SavingGoals> findAll() {
        return repo.findAll();
    }

    public SavingGoals findById(Integer id) {
        return repo.findById(id).orElse(null);
    }

    public SavingGoals save(SavingGoals goal) {
        return repo.save(goal);
    }

    public void deleteById(Integer id) {
        repo.deleteById(id);
    }
}
