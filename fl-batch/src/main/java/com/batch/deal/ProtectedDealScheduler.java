package com.batch.deal;

import com.core.api_core.deal.model.ProtectedDeal;
import com.core.api_core.deal.repository.ProtectedDealRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProtectedDealScheduler {

    private final ProtectedDealRepository protectedDealRepository;
    private final DealCompletionService dealCompletionService;
    private final NotificationService notificationService;

    /**
     * 매일 아침 6시에 실행되어 오늘의 거래 정보를 처리합니다.
     */
    @Scheduled(cron = "0 0 6 * * ?")
    public void processTodayDeals() {
        List<ProtectedDeal> todayDeals = protectedDealRepository.findAll().stream()
                .filter(ProtectedDeal::isDealToday)
                .toList();

        todayDeals.forEach(deal -> {
            try {
                notificationService.sendCompleteDealNotification(deal);
            } catch (Exception e) {
                handleError(e);
            }
        });
    }

    /**
     * 매일 아침 7시에 실행되어 거래 완료 가능한 거래를 처리합니다.
     */
    @Scheduled(cron = "0 0 7 * * ?")
    public void processPassDealDate() {
        List<ProtectedDeal> todayDeals = protectedDealRepository.findAll().stream()
                .filter(ProtectedDeal::isPossibleAutoComplete)
                .toList();

        todayDeals.forEach(deal -> {
            try {
                dealCompletionService.completeDeal(deal);
            } catch (Exception e) {
                handleError(e);
            }
        });
    }

    private void handleError(Exception e) {
        // 예외 처리 로직: 에러 로깅 또는 별도의 처리 방법을 구현
        System.err.println("Error: " + e.getMessage());
        e.printStackTrace();
    }
}