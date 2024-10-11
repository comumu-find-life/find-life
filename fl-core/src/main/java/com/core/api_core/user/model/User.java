package com.core.api_core.user.model;

import com.core.base.model.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

/**
 * 애그리거트 루트
 */
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

    //이메일 회원가입
    private String email;

//    @OneToOne(cascade = CascadeType.ALL)
//    private UserAccount userAccount;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String nickname;

    @NonNull
    private String password;

    private String profileUrl;

    private Integer brith;

    private Integer phoneNumber;

    private String job;

    @Enumerated(EnumType.STRING)
    private UserState userState;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private SignupType signupType;

    private String nationality;
    //자기 소개글
    private String introduce;

    public void passwordEncode(String encodePassword) {
        this.password = encodePassword;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public void setUserState(UserState userState){
        this.userState = userState;
    }

//    public void setPoint(Integer point){
//        userAccount.setPoint(point);
//    }


}