package com.core.api_core.reservation.model;

import com.core.api_core.deal.model.DealState;
import com.core.api_core.deal.model.ProtectedDealDateTime;
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
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;

    private Long homeId;

    private Long dmId;

    // 세입자 ID
    private Long getterId;

    // 집주인 ID
    private Long providerId;

    //보증금 or 계약금
    private int deposit;

    @Enumerated(EnumType.STRING)
    private ReservationState reservationState;

    //예약시간
    private LocalDateTime reservationAt;;
}
