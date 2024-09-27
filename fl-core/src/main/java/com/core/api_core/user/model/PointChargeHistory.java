package com.core.api_core.user.model;


import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PointChargeHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 충전 금액
    private Integer chargeAmount;

    // 충전 일자
    private LocalDateTime chargeDate;

    // 연관 관계 설정: 여러 기록이 하나의 UserAccount에 연결됨
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_account_id")
    private UserAccount userAccount;

    // 기타 필요한 정보 (예: 결제 방법, 결제 상태 등)도 추가 가능
    private String paymentMethod;
}
