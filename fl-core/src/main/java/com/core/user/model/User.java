package com.core.user.model;

import com.core.base.model.BaseTimeEntity;
import com.core.home.model.Home;
import com.core.home.model.HomeAddress;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 애그리거트 루트
 */
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseTimeEntity {

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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_point_id")
    private UserPoint userPoint;

    public double decreasePoint(double price){
        return userPoint.decreasePoint(price);
    }

    public double increasePoint(double price){
        return userPoint.increasePoint(price);
    }

    public void passwordEncode(String encodePassword) {
        this.password = encodePassword;
    }

}