package com.core.api_core.deal.model;

import com.core.base.model.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProtectedDeal extends BaseTimeEntity {

    private static final double chargeRate = 0.03;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "protected_deal_id")
    private Long id;

    // 집 게시글 ID
    private Long homeId;

    // 채팅방 ID
    private Long dmId;

    // 세입자 ID
    private Long getterId;

    // 집주인 ID
    private Long providerId;

    //보증금 or 계약금
    private int deposit;

    private String secretKey;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "protected_deal_time_id")
    private ProtectedDealDateTime protectedDealDateTime;

    @Enumerated(EnumType.STRING)
    private DealState dealState;

    public void setDealState(DealState state) {
        this.dealState = state;
    }

    // 최종 결제 금액 계산 메서드
    public double calculateFinalPayPrice() {
        return deposit - calculateCharge();
    }

    // 수수료 계산 메서드
    public double calculateCharge() {
        return deposit * chargeRate;
    }

}
