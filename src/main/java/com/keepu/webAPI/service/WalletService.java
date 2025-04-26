package com.keepu.webAPI.service;

import com.keepu.webAPI.model.Wallet;
import com.keepu.webAPI.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WalletService {

    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public List<Wallet> findAll() {
        return walletRepository.findAll();
    }

    public Wallet findById(Integer id) {
        return walletRepository.findById(id).orElse(null);
    }

    public Wallet save(Wallet wallet) {
        return walletRepository.save(wallet);
    }

    public void deleteById(Integer id) {
        walletRepository.deleteById(id);
    }
}
