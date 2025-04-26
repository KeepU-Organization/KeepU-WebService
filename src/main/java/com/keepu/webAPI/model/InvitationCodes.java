package com.keepu.webAPI.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Invitation_codes")
public class InvitationCodes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_code;

    private String code;

    @ManyToOne
    @JoinColumn(name = "id_parent")
    private Parent parent;

    private LocalDateTime created_at;
    private LocalDateTime expires_at;
    private Boolean used;
    private String children_name;
}
