package com.keepu.webAPI.model;

import com.keepu.webAPI.model.enums.AuthCodeType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "auth_codes")
public class AuthCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_auth_codes_user"))
    private User user;

    @Column(nullable = false, length = 6)
    private String code;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuthCodeType codeType;

    @Column(name = "is_used", nullable = false)
    private boolean isUsed = false;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    public AuthCode(){
        this.expiresAt = LocalDateTime.now().plusMinutes(5);
        this.code=generateCode();
        this.isUsed = false;
    }
    private String generateCode(){
        return UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt);
    }
}