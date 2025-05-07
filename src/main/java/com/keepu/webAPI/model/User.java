package com.keepu.webAPI.model;

import com.keepu.webAPI.enums.UserType;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "\"user\"")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String lastNames;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserType userType;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean has2FA;

    @Column(nullable = false)
    private String securityKey;

    @Column(nullable = false)
    private boolean isAuthenticated;

    @Column(nullable = false)
    private boolean isActive;
}