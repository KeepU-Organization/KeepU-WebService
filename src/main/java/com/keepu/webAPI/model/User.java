package com.keepu.webAPI.model;

import com.keepu.webAPI.model.enums.UserType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "\"users\"")
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
    private boolean has2FA = false;

    @Column(nullable = false)
    private String securityKey;

    @Column(nullable = false)
    private boolean isAuthenticated = false;

    @Column(nullable = false)
    private boolean isActive = false;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        // La securityKey es igual a la contraseña por defecto
        if (this.securityKey == null && this.password != null) {
            this.securityKey = this.password;
        }
    }


}