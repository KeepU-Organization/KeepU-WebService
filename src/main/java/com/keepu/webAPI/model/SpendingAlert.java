package com.keepu.webAPI.model;

import com.keepu.webAPI.service.AuthService;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class SpendingAlert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer userId;

    //Tipo de transferencia
    private String category;

    //Limite
    private Double threshold;


    private Boolean active;
}
