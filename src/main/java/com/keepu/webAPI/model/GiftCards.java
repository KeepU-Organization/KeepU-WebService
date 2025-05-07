package com.keepu.webAPI.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "gift_cards")
public class GiftCards {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 20)
    private String code;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private boolean isRedeemed;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime redeemedAt;

    @ManyToOne(optional = false)
    @JoinColumn(name = "store_id", nullable = false)
    private Stores store;
}