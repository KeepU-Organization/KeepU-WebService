package com.keepu.webAPI.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "invitation_codes")
public class InvitationCodes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String invitationCode;

    @Column(name = "is_used", nullable = false)
    private boolean isUsed = false;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @Column (name = "child_name", nullable = false)
    private String childName;
    @Column (nullable = false)
    private String childLastName;
    @Column(name = "child_age", nullable = false)
    private Integer childAge;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    public InvitationCodes(){
        this.invitationCode = generateCode();
        this.expiresAt = LocalDateTime.now().plusHours(1);
        this.isUsed = false;
    }
    public String generateCode(){
        return UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }
}