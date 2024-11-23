package com.core.api_core.user.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

import static com.core.api_core.user.model.PointHistory.createHistory;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAccount {
    private static final String ERROR_NOT_ENOUGH_POINT = "포인트가 부족합니다.";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String depositorName;

    private String bsb;

    private String accountNumber;

    private Integer point;

    @OneToMany(mappedBy = "userAccount", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<PointHistory> chargeHistories;

    public void registerPointChargeHistory(int chargeAmount, ChargeType chargeType) {
        PointHistory history = createHistory(this, chargeAmount, chargeType);
        chargeHistories.add(history);
    }

    public void validatePointsSufficiency(int amount) throws IllegalAccessException {
        if (!isEnoughPoint(amount)) {
            throw new IllegalAccessException(ERROR_NOT_ENOUGH_POINT);
        }
    }

    private boolean isEnoughPoint(int amount){
        return this.point - amount >= 0;
    }

    public void decreasePoint(int point) {
        this.point -= point;
    }

    public void increasePoint(int point) {
        this.point += point;
    }


}
