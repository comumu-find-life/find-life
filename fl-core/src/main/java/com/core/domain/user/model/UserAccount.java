package com.core.domain.user.model;

import com.infra.exception.InsufficientPointsException;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

import static com.core.domain.user.model.PointHistory.createHistory;

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

    private double point;

    private String depositorName;

    private String paypalInformation;

    @OneToMany(mappedBy = "userAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PointHistory> chargeHistories;

    public void registerPointChargeHistory(double chargeAmount, ChargeType chargeType) {
        PointHistory history = createHistory(this, chargeAmount, chargeType);
        chargeHistories.add(history);
    }

    public void validatePointsSufficiency(double amount) throws InsufficientPointsException {
        if (!isEnoughPoint(amount)) {
            throw new InsufficientPointsException(ERROR_NOT_ENOUGH_POINT);
        }
    }

    public boolean isEnoughPoint(double amount){
        return this.point - amount >= 0;
    }

    public void decreasePoint(double point) {
        this.point -= point;
    }

    public void increasePoint(double point) {
        this.point += point;
    }


}
