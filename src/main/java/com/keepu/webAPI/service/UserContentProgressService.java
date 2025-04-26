package com.keepu.webAPI.service;

import com.keepu.webAPI.model.UserContentProgress;
import com.keepu.webAPI.repository.UserContentProgressRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserContentProgressService {

    private final UserContentProgressRepository repo;

    public UserContentProgressService(UserContentProgressRepository repo) {
        this.repo = repo;
    }

    public List<UserContentProgress> findAll() {
        return repo.findAll();
    }

    public UserContentProgress findById(Integer id) {
        return repo.findById(id).orElse(null);
    }

    public UserContentProgress save(UserContentProgress p) {
        return repo.save(p);
    }

    public void deleteById(Integer id) {
        repo.deleteById(id);
    }
}
