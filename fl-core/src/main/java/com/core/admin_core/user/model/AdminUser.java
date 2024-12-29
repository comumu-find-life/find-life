package com.core.admin_core.user.model;

import com.core.base.model.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class AdminUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password; // 암호화 없이 저장

    @Column(nullable = false)
    private String role; // 예: "ADMIN"
}