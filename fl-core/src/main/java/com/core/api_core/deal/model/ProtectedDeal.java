package com.core.api_core.deal.model;

import com.core.api_core.home.model.HomeAddress;
import com.core.base.model.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    // 세입자 ID
    private Long getterId;

    // 집주인 ID
    private Long providerId;

    private Long homeId;

    private Long dmId;

    //보증금 or 계약금
    private double deposit;

    //계좌
//    private String account;
//
//    private String accountHolder;
//
//    private String bankName;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "provider_account_id")
    private ProviderAccount providerAccount;

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
