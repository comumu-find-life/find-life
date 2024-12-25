package com.batch.deal;

import com.core.api_core.deal.model.DealState;
import com.core.api_core.deal.model.ProtectedDeal;
import com.core.api_core.deal.repository.ProtectedDealRepository;
import com.core.api_core.home.model.Home;
import com.core.api_core.home.model.HomeStatus;
import com.core.api_core.home.repository.HomeRepository;
import com.core.api_core.user.model.User;
import com.core.api_core.user.model.UserAccount;
import com.core.api_core.user.repository.UserAccountRepository;
import com.core.api_core.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.service.home.HomeMessages.NOT_EXIST_HOME_ID;

@Service
@RequiredArgsConstructor
public class DealCompletionService {

    private final NotificationService notificationService;
    private final UserAccountRepository userAccountRepository;
    private final UserRepository userRepository;
    private final HomeRepository homeRepository;

    public void completeDeal(ProtectedDeal protectedDeal) throws IllegalAccessException {
        UserAccount userAccount = userAccountRepository.findByUserId(protectedDeal.getProviderId())
                .orElseThrow(() -> new IllegalArgumentException("User Account not found for provider ID"));
        User providerAccount = userRepository.findById(protectedDeal.getProviderId())
                .orElseThrow(() -> new IllegalArgumentException("Provider not found"));
        userAccount.increasePoint(protectedDeal.getDeposit());
        Home home = homeRepository.findById(protectedDeal.getHomeId())
                .orElseThrow(() -> new IllegalArgumentException(NOT_EXIST_HOME_ID));
        home.setHomeStatus(HomeStatus.SOLD_OUT);
        protectedDeal.setDealState(DealState.COMPLETE_DEAL);
        protectedDeal.getProtectedDealDateTime().setCompleteAt(LocalDateTime.now());
        // FCM 알림 전송
        notificationService.sendAutoCompleteDealNotification(providerAccount.getFcmToken());
    }
}