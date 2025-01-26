package com.core.api_core.deal.model;

import com.core.api_core.base.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProtectedDeal extends BaseTimeEntity {

    private static final double feeRate = 0.05;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "protected_deal_id")
    private Long id;

    private Long homeId;

    private Long dmId;

    private Long getterId;

    private Long providerId;

    private double deposit;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "protected_deal_time_id")
    private ProtectedDealDateTime protectedDealDateTime;

    @Enumerated(EnumType.STRING)
    private DealState dealState;


    public double calculateTotalPrice() {
        return deposit + calculateFee();
    }

    public double calculateFee() {
        return Math.round(deposit * feeRate * 100.0) / 100.0;
    }

    public boolean isDealToday(){
        return protectedDealDateTime.isToday();
    }

    public boolean isPossibleAutoComplete(){
        return protectedDealDateTime.isFiveDaysPassed() && (dealState.equals(DealState.ACCEPT_DEAL));
    }

    public void setDealState(DealState state) {
        this.dealState = state;
    }
}
