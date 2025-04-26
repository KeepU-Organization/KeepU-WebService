package com.keepu.webAPI.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "Wallet")
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_wallet;

    @ManyToOne
    @JoinColumn(name = "id_wallet_type")
    private WalletTypes walletType;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    private BigDecimal balance;

    @OneToMany(mappedBy = "wallet")
    private List<SavingGoals> savingGoals;

    @OneToMany(mappedBy = "wallet")
    private List<SpendingLimits> spendingLimits;
}
