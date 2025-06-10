package com.keepu.webAPI.service;

import com.keepu.webAPI.dto.request.CreateTransactionRequest;
import com.keepu.webAPI.dto.request.CreateWalletRequest;
import com.keepu.webAPI.dto.response.GiftCardResponse;
import com.keepu.webAPI.dto.response.ShopResponse;
import com.keepu.webAPI.dto.response.TransferResponse;
import com.keepu.webAPI.dto.response.WalletResponse;
import com.keepu.webAPI.exception.InsufficientFundsException;
import com.keepu.webAPI.exception.NotFoundException;
import com.keepu.webAPI.mapper.WalletMapper;
import com.keepu.webAPI.model.GiftCards;
import com.keepu.webAPI.model.Stores;
import com.keepu.webAPI.model.User;
import com.keepu.webAPI.model.Wallet;
import com.keepu.webAPI.model.enums.TransactionType;
import com.keepu.webAPI.model.enums.WalletType;
import com.keepu.webAPI.repository.GiftCardsRepository;
import com.keepu.webAPI.repository.StoresRepository;
import com.keepu.webAPI.repository.UserRepository;
import com.keepu.webAPI.repository.WalletRepository;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.Store;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.math.BigDecimal;
import java.util.List;

import static java.math.BigInteger.ZERO;

@Service
@RequiredArgsConstructor

public class WalletService {

    private final WalletRepository walletRepository;
    private final UserRepository userRepository;
    private final WalletMapper walletMapper;
    private final TransactionsService transactionsService;
    private final GiftCardsRepository giftCardsRepository;
    private final StoresRepository storeRepository;
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
    public WalletResponse getWalletByUserId(Long userId) {
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

        CreateTransactionRequest transactionRequest = new CreateTransactionRequest(
                updatedWallet, amount, "Depósito realizado", TransactionType.DEPOSIT, null, null);
        transactionsService.recordTransfer(transactionRequest);
        return walletMapper.toWalletResponse(updatedWallet);
    }
    @Transactional
    public TransferResponse transfer(String senderWalletId, String receiverWalletId, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto a transferir debe ser mayor que cero");
        }

        Wallet senderWallet = walletRepository.findByWalletId(senderWalletId)
                .orElseThrow(() -> new NotFoundException("Billetera de origen no encontrada"));
        Wallet receiverWallet = walletRepository.findByWalletId(receiverWalletId)
                .orElseThrow(() -> new NotFoundException("Billetera de destino no encontrada"));

        if (senderWallet.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException("Saldo insuficiente para realizar la transferencia");
        }

        if (receiverWallet.getWalletType() == WalletType.PARENT) {
            throw new IllegalArgumentException("No se puede transferir a una billetera de tipo PARENT");
        }

        senderWallet.setBalance(senderWallet.getBalance().subtract(amount));
        receiverWallet.setBalance(receiverWallet.getBalance().add(amount));

        Wallet updatedSenderWallet = walletRepository.save(senderWallet);
        Wallet updatedReceiverWallet = walletRepository.save(receiverWallet);

        // Registrar la transacción SOLO para el sender
        CreateTransactionRequest transactionRequest = new CreateTransactionRequest(
                updatedSenderWallet, amount, "Transferencia realizada a " + receiverWallet.getUser().getName(), TransactionType.TRANSFER, null, null);
        transactionsService.recordTransfer(transactionRequest);

        return new TransferResponse(
                walletMapper.toWalletResponse(updatedSenderWallet),
                walletMapper.toWalletResponse(updatedReceiverWallet),
                amount
        );
    }
    @Transactional
    public ShopResponse purchaseGiftCard(String walletId, Integer storeId, Integer amountGiftCards, BigDecimal amount) {
        Wallet wallet = walletRepository.findByWalletId(walletId)
                .orElseThrow(() -> new NotFoundException("Billetera no encontrada"));

        if (amountGiftCards <= 0) {
            throw new IllegalArgumentException("La cantidad de gift cards debe ser mayor que cero");
        }

        Stores store = storeRepository.findById(storeId)
                .orElseThrow(() -> new NotFoundException("Tienda no encontrada"));
        List<GiftCards> giftcards = giftCardsRepository.findByStore(store);

        if (giftcards.isEmpty() || giftcards.size() < amountGiftCards ) {
            throw new NotFoundException("No hay gift cards disponibles para esta tienda");
        }

        if (wallet.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException("Saldo insuficiente para realizar la compra");
        }

        for (int i = 0; i < amountGiftCards; i++) {
            GiftCards giftCard = giftcards.get(i);
            if (giftCard.isRedeemed()) {
                throw new IllegalArgumentException("La gift card con código " + giftCard.getCode() + " ya ha sido canjeada");
            }
            giftCard.setRedeemed(true);
            giftCard.setRedeemedAt(java.time.LocalDateTime.now());
            giftCardsRepository.save(giftCard);

            CreateTransactionRequest transactionRequest = new CreateTransactionRequest(
                    wallet, giftCard.getAmount(), "Compra de gift card: " + giftCard.getCode(), TransactionType.PURCHASE, giftCard, store);

            transactionsService.recordTransfer(transactionRequest);
        }

        wallet.setBalance(wallet.getBalance().subtract(amount));
        Wallet updatedWallet = walletRepository.save(wallet);
        return new  ShopResponse(
                walletMapper.toWalletResponse(updatedWallet),
                giftcards,store.getName(),amount
        );
    }
    public List<Wallet> getAllWallets() {
        return walletRepository.findAll();
    }

}