package com.keepu.webAPI.model;

import com.keepu.webAPI.model.enums.WalletType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "wallets")
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String walletId;

    @Enumerated(EnumType.STRING)
    @Column(name = "wallet_type", nullable = false)
    private WalletType walletType;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal balance;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    @Column
    private boolean active;

    public Wallet() {
        this.walletId = generateWalletId();
        this.balance= BigDecimal.ZERO;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.active = true;
    }
    public Wallet(User user,WalletType walletType) {
        this();
        this.user = user;
        this.walletType = walletType;
    }
    private String generateWalletId() {
        return "W-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
        this.updatedAt = LocalDateTime.now();
    }
    public void setActive(boolean active) {
        this.active = active;
        this.updatedAt = LocalDateTime.now();
    }

}