package com.keepu.webAPI.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor // Constructor sin argumentos (requerido por JPA)
@AllArgsConstructor // Constructor con todos los campos (opcional)
@Entity
@Table(name = "medal_progress")
public class MedalProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "medal_name", nullable = false)
    private String medalName;

    @Column(name = "progress", nullable = false)
    private Integer progress;

    @Column(name = "completed", nullable = false)
    private Boolean completed;

    // Evitamos carga inmediata con FetchType.LAZY para mejorar rendimiento
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "children_id", nullable = false)
    @ToString.Exclude  // Excluye children de toString() para evitar bucles infinitos
    @EqualsAndHashCode.Exclude // Excluye children de equals y hashCode para evitar problemas con proxies
    private Children children;
}
