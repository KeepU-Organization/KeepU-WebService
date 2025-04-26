package com.keepu.webAPI.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "Saving_goals")
public class SavingGoals {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_goal;

    @ManyToOne
    @JoinColumn(name = "id_wallet")
    private Wallet wallet;

    private BigDecimal target_amount;
    private String description;
    private Boolean achieved;
}
