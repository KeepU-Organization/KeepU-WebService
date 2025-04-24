package com.keepu.webAPI.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Users")
@Data
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_user;

    private String name;
    private String last_names;
    private Integer id_user_type;
    private String email;
    private String password;
}