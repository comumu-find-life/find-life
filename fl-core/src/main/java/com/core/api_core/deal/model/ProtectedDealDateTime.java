package com.core.api_core.deal.model;

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
public class ProtectedDealDateTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "protected_deal_time_id")
    private Long id;

    //거래 시작 시간
    private LocalDateTime dealStartDateTime;

    //입금 신청 시간
    private LocalDateTime depositRequestDateTime;

    //입금 완료 시간
    private LocalDateTime depositCompletionDateTime;

    //거래 완료 신청 시간
    private LocalDateTime dealCompletionRequestDateTime;

    //거래 종료 시간
    private LocalDateTime dealCompletionDateTime;

    //거래 취소 시간
    private LocalDateTime dealCancellationDateTime;
}
