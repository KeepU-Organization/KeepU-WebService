package com.keepu.webAPI.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "Spending_limits")
public class SpendingLimits {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_limit;

    @ManyToOne
    @JoinColumn(name = "id_wallet")
    private Wallet wallet;

    private BigDecimal max_amount;
}
