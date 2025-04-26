package com.keepu.webAPI.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Transactions")
public class Transactions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_transaction;

    @ManyToOne
    @JoinColumn(name = "id_sender")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "id_user_receiver")
    private User userReceiver;

    @ManyToOne
    @JoinColumn(name = "id_store_receiver")
    private Stores storeReceiver;

    private BigDecimal amount;
    private LocalDateTime date;
    private String description;
}
