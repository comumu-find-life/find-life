package com.batch.deal;

import com.core.domain.deal.model.DealState;
import com.core.domain.deal.model.ProtectedDeal;
import com.core.domain.home.model.Home;
import com.core.domain.home.model.HomeStatus;
import com.core.domain.home.repository.HomeRepository;
import com.core.domain.user.model.User;
import com.core.domain.user.model.UserAccount;
import com.core.domain.user.repository.UserAccountRepository;
import com.core.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class DealCompletionService {

    private final UserAccountRepository userAccountRepository;
    private final UserRepository userRepository;
    private final HomeRepository homeRepository;

    public String completeDeal(final ProtectedDeal protectedDeal) {
        UserAccount providerAccount = userAccountRepository.findByUserId(protectedDeal.getProviderId()).get();
        User provider = userRepository.findById(protectedDeal.getProviderId()).get();
        providerAccount.increasePoint(protectedDeal.getDeposit());
        Home home = homeRepository.findById(protectedDeal.getHomeId()).get();
        home.setHomeStatus(HomeStatus.SOLD_OUT);
        protectedDeal.setDealState(DealState.COMPLETE_DEAL);
        protectedDeal.getProtectedDealDateTime().setCompleteAt(LocalDateTime.now());
        return provider.getFcmToken();
    }
}