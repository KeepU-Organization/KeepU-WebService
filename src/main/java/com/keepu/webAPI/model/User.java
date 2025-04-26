package com.keepu.webAPI.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "\"User\"")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_user;

    private String name;
    private String last_names;

    @ManyToOne
    @JoinColumn(name = "id_user_type")
    private UserTypes userType;

    private String email;
    private String password;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Parent parent;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Children children;

    @OneToMany(mappedBy = "user")
    private List<Wallet> wallets;

    @OneToMany(mappedBy = "sender")
    private List<Transactions> sentTransactions;

    @OneToMany(mappedBy = "userReceiver")
    private List<Transactions> receivedTransactions;
}

