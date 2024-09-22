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

    // 입금자 명 (랜덤 생성)
    private String randomDepositorName;

    // 세입자 ID
    private Long getterId;

    // 집주인 ID
    private Long providerId;

    //보증금 or 계약금
    private double deposit;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "provider_account_id")
    private ProviderAccount providerAccount;

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


    //입금 완료 시간 설정
    public void setDepositCompletionDateTime(LocalDateTime time){
        this.protectedDealDateTime.setDepositCompletionDateTime(time);
    }

    // 입근 신청 시간 설정
    public void setDepositRequestDateTime(LocalDateTime time){
        this.protectedDealDateTime.setDepositRequestDateTime(time);
    }

    // 입금 취소 시간 설정
    public void setDepositCancelDateTime(LocalDateTime time){
        this.protectedDealDateTime.setDepositCancelDateTime(time);
    }

    // 거래 완료 신청 시간 설정
    public void setDealCompletionRequestDateTime(LocalDateTime time){
        this.protectedDealDateTime.setDealCompletionRequestDateTime(time);
    }

    // 거래 종료 시간 설정
    public void setDealCompleteDateTime(LocalDateTime time) {
        this.protectedDealDateTime.setDealCompletionDateTime(time);
    }

    //거래 취소 시간 설정
    public void setDealCancellationDateTime(LocalDateTime time){
        this.protectedDealDateTime.setDealCancellationDateTime(time);
    }


}
