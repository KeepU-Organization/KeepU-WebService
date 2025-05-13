package com.keepu.webAPI.service;

import com.keepu.webAPI.dto.request.CreateWalletRequest;
import com.keepu.webAPI.dto.response.TransferResponse;
import com.keepu.webAPI.dto.response.WalletResponse;
import com.keepu.webAPI.exception.InsufficientFundsException;
import com.keepu.webAPI.exception.NotFoundException;
import com.keepu.webAPI.mapper.WalletMapper;
import com.keepu.webAPI.model.User;
import com.keepu.webAPI.model.Wallet;
import com.keepu.webAPI.model.enums.WalletType;
import com.keepu.webAPI.repository.UserRepository;
import com.keepu.webAPI.repository.WalletRepository;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

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
    @Transactional
    public WalletResponse getWalletByUserId(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        Wallet wallet = walletRepository.findByUser(user)
                .orElseThrow(() -> new NotFoundException("Billetera no encontrada"));
        return walletMapper.toWalletResponse(wallet);
    }
    @Transactional
    public WalletResponse getWalletByWalletId(String walletId) {
        Wallet wallet = walletRepository.findByWalletId(walletId)
                .orElseThrow(() -> new NotFoundException("Billetera no encontrada"));
        return walletMapper.toWalletResponse(wallet);
    }

    @Transactional
    public WalletResponse updateBalance(User user, BigDecimal  newBalance) {
        Wallet wallet = walletRepository.findByUser(user)
                .orElseThrow(() -> new NotFoundException("Billetera no encontrada"));
        wallet.setBalance(newBalance);
        Wallet updatedWallet = walletRepository.save(wallet);
        return walletMapper.toWalletResponse(updatedWallet);
    }
    @Transactional
    public WalletResponse deposit(String walletId, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO)<=0){
            throw new IllegalArgumentException("El monto a depositar debe ser mayor que cero");
        }
        Wallet wallet = walletRepository.findByWalletId(walletId)
                .orElseThrow(() -> new NotFoundException("Billetera no encontrada"));

        wallet.setBalance(wallet.getBalance().add(amount));
        Wallet updatedWallet = walletRepository.save(wallet);
        return walletMapper.toWalletResponse(updatedWallet);
    }
    @Transactional
    public TransferResponse transfer(String senderWalletId, String receiverWalletId, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO)<=0){
            throw new IllegalArgumentException("El monto a transferir debe ser mayor que cero");
        }
        Wallet senderWallet = walletRepository.findByWalletId(senderWalletId)
                .orElseThrow(() -> new NotFoundException("Billetera de origen no encontrada"));
        Wallet receiverWallet = walletRepository.findByWalletId(receiverWalletId)
                .orElseThrow(() -> new NotFoundException("Billetera de destino no encontrada"));

        if (senderWallet.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException("Saldo insuficiente para realizar la transferencia");
        }
        if (receiverWallet.getWalletType() == WalletType.PARENT){
            throw new IllegalArgumentException("No se puede transferir a una billetera de tipo PARENT");
        }
        senderWallet.setBalance(senderWallet.getBalance().subtract(amount));
        receiverWallet.setBalance(receiverWallet.getBalance().add(amount));

        Wallet updatedSenderWallet= walletRepository.save(senderWallet);
        Wallet updatedReceiverWallet= walletRepository.save(receiverWallet);

        return new TransferResponse(walletMapper.toWalletResponse(updatedSenderWallet),
                walletMapper.toWalletResponse(updatedReceiverWallet),
                amount);
    }

}