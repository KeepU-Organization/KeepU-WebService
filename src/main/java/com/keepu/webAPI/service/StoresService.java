package com.keepu.webAPI.service;

import com.keepu.webAPI.model.Stores;
import com.keepu.webAPI.repository.StoresRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoresService {

    private final StoresRepository repo;

    public StoresService(StoresRepository repo) {
        this.repo = repo;
    }

    public List<Stores> findAll() {
        return repo.findAll();
    }

    public Stores findById(Integer id) {
        return repo.findById(id).orElse(null);
    }

    public Stores save(Stores s) {
        return repo.save(s);
    }

    public void deleteById(Integer id) {
        repo.deleteById(id);
    }
}
