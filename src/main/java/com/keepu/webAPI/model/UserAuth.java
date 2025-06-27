package com.keepu.webAPI.model;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "user_auth")
public class UserAuth {

    @Id
    private Long userId;  // Campo explícito para la clave primaria

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    @MapsId
    private User user;


    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean has2FA;

    @Column(nullable = false)
    private String securityKey;

    @Column(name = "email_verified", nullable = false)
    private boolean emailVerified;


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
