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

    private LocalDateTime createAt;

    private LocalDateTime startAt;

    private LocalDateTime cancelAt;

    private LocalDateTime completeAt;

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
