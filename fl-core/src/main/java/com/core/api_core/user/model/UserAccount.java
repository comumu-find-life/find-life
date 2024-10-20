package com.core.api_core.user.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

import static com.core.api_core.user.model.PointChargeHistory.createHistory;

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

    private Long userId;

    private String depositorName;

    private String bsb;

    private String accountNumber;

    private Integer point;

    @OneToMany(mappedBy = "userAccount", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<PointChargeHistory> chargeHistories;

    // 포인트 충전 기록을 추가하는 메서드
    public void addChargeHistory(PointChargeHistory history) {
        this.chargeHistories.add(history);
        history.setUserAccount(this);
    }

    public void registerPointChargeHistory(int chargeAmount) {
        PointChargeHistory history = createHistory(this, chargeAmount);
        chargeHistories.add(history);
    }

    public void validatePointsSufficiency(int amount) throws IllegalAccessException {
        if (this.point - amount < 0) {
            throw new IllegalAccessException("포인트가 부족합니다.");
        }
    }

    public void decreasePoint(int point) {
        this.point -= point;
    }

    public void increasePoint(int point) {
        this.point += point;
    }


}
