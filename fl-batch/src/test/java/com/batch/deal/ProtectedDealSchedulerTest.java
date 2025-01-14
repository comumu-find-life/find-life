//package com.batch.deal;
//
//import com.core.api_core.deal.model.ProtectedDeal;
//import com.core.api_core.deal.repository.ProtectedDealRepository;
//import com.batch.deal.NotificationService;
//import com.batch.deal.DealCompletionService;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.scheduling.annotation.EnableScheduling;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//import static org.mockito.Mockito.*;
//
//@SpringBootTest
//@EnableScheduling
//public class ProtectedDealSchedulerTest {
//
//    @Mock
//    private ProtectedDealRepository protectedDealRepository; // ProtectedDealRepository 모의 객체
//
//    @Mock
//    private NotificationService notificationService; // NotificationService 모의 객체
//
//    @Mock
//    private DealCompletionService dealCompletionService; // DealCompletionService 모의 객체
//
//    @InjectMocks
//    private ProtectedDealScheduler protectedDealScheduler; // 실제 객체에 의존성 주입
//
//    @Test
//    public void testProcessTodayDeals() {
//
//
//        // 2. ProtectedDealRepository가 반환할 값 설정
//        when(protectedDealRepository.findAll()).thenReturn(List.of(deal1, deal2));
//
//        // 3. 테스트 실행: processTodayDeals 메서드 호출
//        protectedDealScheduler.processTodayDeals();
//
//        // 4. 호출된 메서드 검증: 알림 서비스가 실행되었는지 확인
//        verify(notificationService, times(2)).sendCompleteDealNotification(any(ProtectedDeal.class)); // 두 번 호출되어야 함
//    }
//}
