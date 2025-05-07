package com.keepu.webAPI.mapper;

import com.keepu.webAPI.dto.request.CreateWalletRequest;
import com.keepu.webAPI.dto.response.WalletResponse;
import com.keepu.webAPI.model.Wallet;
import com.keepu.webAPI.model.User;
import org.springframework.stereotype.Component;

@Component
public class WalletMapper {

    public WalletResponse toWalletResponse(Wallet wallet) {
        if (wallet == null) {
            return null;
        }
        return new WalletResponse(
                wallet.getId(),
                wallet.getWalletType(),
                wallet.getBalance(),
                wallet.getUser().getId()
        );
    }

    public Wallet toWalletEntity(CreateWalletRequest request, User user) {
        if (request == null || user == null) {
            return null;
        }

        Wallet wallet = new Wallet();
        wallet.setWalletType(request.walletType());
        wallet.setBalance(0.0);
        wallet.setUser(user);
        return wallet;
    }
}