package com.keepu.webAPI.model;

import com.keepu.webAPI.model.enums.TransactionType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "transactions")
public class Transactions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private String description;

    @Column(name = "transaction_date", nullable = false)
    private LocalDateTime transactionDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false)
    private TransactionType transactionType;

    @ManyToOne(optional = true)
    @JoinColumn(name = "gift_card_id", foreignKey = @ForeignKey(name = "fk_transactions_gift_card"))
    private GiftCards giftCard;

    @ManyToOne(optional = false)
    @JoinColumn(name = "wallet_id", nullable = false)
    private Wallet wallet;

    @ManyToOne(optional = true)
    @JoinColumn(name = "store_id", foreignKey = @ForeignKey(name = "fk_transactions_store"))
    private Stores store;
}