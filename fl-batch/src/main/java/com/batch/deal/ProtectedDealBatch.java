package com.batch.deal;

import com.core.api_core.deal.model.ProtectedDeal;
import com.core.api_core.deal.repository.ProtectedDealRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class ProtectedDealBatch {

    private final ProtectedDealRepository protectedDealRepository;

    @Scheduled(cron = "0 0 6 * * ?") // 매일 아침 6시 실행
    public void processTodayDeals() {
        // 오늘 날짜의 ProtectedDeal 조회
        List<ProtectedDeal> todayDeals = protectedDealRepository.findAll().stream()
                .filter(ProtectedDeal::isDealToday)
                .toList();

        // 알림 전송 로직은 여기에 추가
        todayDeals.forEach(deal -> {
            // FCM 알림 전송 기능 호출
            System.out.println("알림 전송 대상: " + deal.getId());
        });
    }
}