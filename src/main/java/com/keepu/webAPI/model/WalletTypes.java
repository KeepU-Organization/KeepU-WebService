package com.keepu.webAPI.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Wallet_types")
public class WalletTypes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_wallet_type;

    private String type;
}
