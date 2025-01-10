package com.core.api_core.user.model;

import com.core.base.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "`user`")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String email;

    private String nickname;

    @NonNull
    private String password;

    private String profileUrl;

    private Integer brith;

    private Integer phoneNumber;

    private String fcmToken;

    private String job;

    @Enumerated(EnumType.STRING)
    private UserState userState;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private SignupType signupType;

    private String nationality;

    private String introduce;

    private String refreshToken;

    public void passwordEncode(String encodePassword) {
        this.password = encodePassword;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public void setRefreshToken(String refreshToken){
        this.refreshToken = refreshToken;
    }

    public void setUserState(UserState userState){
        this.userState = userState;
    }

}