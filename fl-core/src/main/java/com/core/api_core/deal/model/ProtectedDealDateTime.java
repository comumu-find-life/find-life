package com.core.api_core.deal.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProtectedDealDateTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "protected_deal_time_id")
    private Long id;

    //거래 생성 시간
    private LocalDateTime createAt;

    //거래 시작 시간
    private LocalDateTime startAt;

    //거래 취소 시간
    private LocalDateTime cancelAt;

    //거래 완료 시간
    private LocalDateTime completeAt;

    //거래 날짜
    private LocalDateTime dealAt;

    public boolean isToday() {
        LocalDate today = LocalDate.now();
        return dealAt != null && dealAt.toLocalDate().isEqual(today);
    }

    public boolean isFiveDaysPassed() {
        LocalDate today = LocalDate.now();
        return dealAt != null && dealAt.toLocalDate().isBefore(today.minusDays(5));
    }
}
