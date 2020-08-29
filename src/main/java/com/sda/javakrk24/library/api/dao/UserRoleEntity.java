package com.sda.javakrk24.library.api.dao;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "app_users_roles")
public class UserRoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private UserEntity user;
    private String role;
}
