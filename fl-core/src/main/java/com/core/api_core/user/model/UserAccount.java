package com.core.api_core.user.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bsb;

    private String accountNumber;

    private Integer point;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    // UserAccount와 PointChargeHistory는 1:N 관계
    @OneToMany(mappedBy = "userAccount", cascade = CascadeType.ALL)
    private List<PointChargeHistory> chargeHistories;

    // 포인트 충전 기록을 추가하는 메서드
    public void addChargeHistory(PointChargeHistory history) {
        this.chargeHistories.add(history);
        history.setUserAccount(this);
    }
}
