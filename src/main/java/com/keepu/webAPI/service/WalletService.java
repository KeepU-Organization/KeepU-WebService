package com.keepu.webAPI.service;

import com.keepu.webAPI.dto.request.CreateWalletRequest;
import com.keepu.webAPI.dto.response.WalletResponse;
import com.keepu.webAPI.exception.NotFoundException;
import com.keepu.webAPI.mapper.WalletMapper;
import com.keepu.webAPI.model.User;
import com.keepu.webAPI.model.Wallet;
import com.keepu.webAPI.repository.UserRepository;
import com.keepu.webAPI.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;
    private final UserRepository userRepository;
    private final WalletMapper walletMapper;

    @Transactional
    public WalletResponse createWallet(CreateWalletRequest request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        Wallet wallet = walletMapper.toWalletEntity(request, user);
        Wallet savedWallet = walletRepository.save(wallet);

        return walletMapper.toWalletResponse(savedWallet);
    }

    @Transactional(readOnly = true)
    public WalletResponse getWalletById(Integer id) {
        Wallet wallet = walletRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Billetera no encontrada"));
        return walletMapper.toWalletResponse(wallet);
    }

    public WalletResponse getWalletByUserId(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        Wallet wallet = walletRepository.findByUser(user)
                .orElseThrow(() -> new NotFoundException("Billetera no encontrada"));
        return walletMapper.toWalletResponse(wallet);
    }
}