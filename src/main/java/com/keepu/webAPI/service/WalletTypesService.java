package com.keepu.webAPI.service;

import com.keepu.webAPI.model.WalletTypes;
import com.keepu.webAPI.repository.WalletTypesRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class WalletTypesService {

    private final WalletTypesRepository repo;

    public WalletTypesService(WalletTypesRepository repo) {
        this.repo = repo;
    }

    public List<WalletTypes> findAll() {
        return repo.findAll();
    }

    public WalletTypes findById(Integer id) {
        return repo.findById(id).orElse(null);
    }

    public WalletTypes save(WalletTypes w) {
        return repo.save(w);
    }

    public void deleteById(Integer id) {
        repo.deleteById(id);
    }
}
