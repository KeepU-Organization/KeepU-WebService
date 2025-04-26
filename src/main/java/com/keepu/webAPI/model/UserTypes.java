package com.keepu.webAPI.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "User_types")
public class UserTypes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_user_type;

    private String type;
}
