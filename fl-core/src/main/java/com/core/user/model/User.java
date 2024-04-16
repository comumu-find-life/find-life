package com.core.user.model;

import com.core.base.model.BaseTimeEntity;
import com.core.home.model.Home;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User  extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String nickName;

    private String password;

    private String profileUrl;

    private Integer brith;

    private Integer phoneNumber;

    private Gender gender;

    private String nationality;

    private String refreshToken; // 리프레시 토큰

    public void passwordEncode(String encodePassword) {
        this.password = encodePassword;
        //this.password = passwordEncoder.encode(this.password);
    }
//
    public void updateRefreshToken(String updateRefreshToken) {
        this.refreshToken = updateRefreshToken;
    }

}