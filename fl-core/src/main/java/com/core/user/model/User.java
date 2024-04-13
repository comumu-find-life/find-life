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

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Home> homes;

//    @JsonIgnore
//    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = true)
//    private List<Job> jobs;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String refreshToken; // 리프레시 토큰

    private String nickName;

    private String email;

    private String profileUrl;

    private String password;

    private Integer brith;

    private Integer phoneNumber;

    private Gender gender;

    private String nationality;

    public void passwordEncode(String encodePassword) {
        this.password = encodePassword;
        //this.password = passwordEncoder.encode(this.password);
    }
//
    public void updateRefreshToken(String updateRefreshToken) {
        this.refreshToken = updateRefreshToken;
    }

}