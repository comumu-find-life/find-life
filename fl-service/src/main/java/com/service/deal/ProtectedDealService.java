package com.service.deal;

import com.common.deal.mapper.ProtectedDealMapper;
import com.common.deal.request.ProtectedDealFindRequest;
import com.common.deal.request.ProtectedDealGeneratorRequest;
import com.common.deal.response.ProtectedDealGeneratorResponse;
import com.common.deal.response.ProtectedDealResponse;
import com.common.fcm.FCMHelper;
import com.common.fcm.FCMState;
import com.core.api_core.deal.model.DealState;
import com.core.api_core.deal.model.ProtectedDeal;
import com.core.api_core.deal.repository.ProtectedDealRepository;
import com.core.api_core.home.model.Home;
import com.core.api_core.home.model.HomeStatus;
import com.core.api_core.home.repository.HomeRepository;
import com.common.utils.OptionalUtil;
import com.core.api_core.user.model.User;
import com.core.api_core.user.model.UserAccount;
import com.core.api_core.user.repository.UserAccountRepository;
import com.core.api_core.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.service.deal.ProtectedDealMessages.DEAL_NOT_FOUND;
import static com.service.home.HomeMessages.NOT_EXIST_HOME_ID;
import static com.service.user.UserMessages.NOT_EXIT_USER_ID;

@Transactional
@Service
@RequiredArgsConstructor
public class ProtectedDealService {

    private final FCMHelper fcmHelper;
    private final ProtectedDealRepository protectedDealRepository;
    private final UserAccountRepository userAccountRepository;
    private final UserRepository userRepository;
    private final HomeRepository homeRepository;
    private final ProtectedDealMapper mapper;

    /**
     * 안전 거래 생성 메서드 (by provider)
     */
    public ProtectedDealGeneratorResponse saveProtectedDeal(ProtectedDealGeneratorRequest request) {
        ProtectedDeal deal = mapper.toEntity(request);
        Long dealId = protectedDealRepository.save(deal).getId();
        Home home = OptionalUtil.getOrElseThrow(homeRepository.findById(request.getHomeId()), NOT_EXIST_HOME_ID);
//        home.setHomeStatus(HomeStatus.DURING_SELL);
        ProtectedDealGeneratorResponse protectedDealGeneratorResponse = mapper.toGeneratorResponse(dealId);
        return protectedDealGeneratorResponse;
    }

    /**
     * 내 안전 거래 조회 메서드
     */
    public List<ProtectedDealResponse> findAllByUserId(Long userId) {
        List<ProtectedDeal> allByUserId = protectedDealRepository.findAllByUserId(userId);
        List<ProtectedDealResponse> response = new ArrayList<>();
        allByUserId.stream()
                .forEach(protectedDeal -> {
                    Home home = OptionalUtil.getOrElseThrow(homeRepository.findById(protectedDeal.getHomeId()), NOT_EXIST_HOME_ID);
                    response.add(mapper.toResponse(protectedDeal, home));
                });
        return response;
    }

    /**
     * 안전거래 조회 메서드 by Getter
     */
    public List<ProtectedDealResponse> findProtectedDeal(ProtectedDealFindRequest request) {
        List<ProtectedDealResponse> responses = new ArrayList<>();
        List<ProtectedDeal> protectedDeals = protectedDealRepository.findByMultipleParams(request.getGetterId(), request.getProviderId(), request.getHomeId(), request.getDmId());
        protectedDeals.stream()
                .forEach(protectedDeal -> {
                    Home home = OptionalUtil.getOrElseThrow(homeRepository.findById(protectedDeal.getHomeId()), NOT_EXIST_HOME_ID);
                    responses.add(mapper.toResponse(protectedDeal, home));
                });
        return responses;
    }


    /**
     * 안전 거래 수락 by getter
     */
    @CacheEvict(value = "homeOverviewCache", key = "'allHomes'", allEntries = true)
    @Transactional
    public void acceptProtectedDeal(Long dealId) throws Exception {
        ProtectedDeal protectedDeal = OptionalUtil.getOrElseThrow(protectedDealRepository.findById(dealId), DEAL_NOT_FOUND);
        User getter = OptionalUtil.getOrElseThrow(userRepository.findById(protectedDeal.getGetterId()), NOT_EXIT_USER_ID);
        UserAccount userAccount = userAccountRepository.findByUserId(getter.getId()).get();
        userAccount.validatePointsSufficiency(protectedDeal.calculateTotalPrice());
        userAccount.decreasePoint(protectedDeal.calculateTotalPrice());
        protectedDeal.setDealState(DealState.ACCEPT_DEAL);
        protectedDeal.getProtectedDealDateTime().setStartAt(LocalDateTime.now());
    }


    /**
     * 거래 완료 메서드 by getter
     */
    @CacheEvict(value = "homeOverviewCache", key = "'allHomes'", allEntries = true)
    @Transactional
    public void completeDeal(Long dealId) throws IllegalAccessException {
        ProtectedDeal protectedDeal = OptionalUtil.getOrElseThrow(protectedDealRepository.findById(dealId), DEAL_NOT_FOUND);
        User provider = userRepository.findById(protectedDeal.getProviderId()).get();
        UserAccount providerAccount = userAccountRepository.findByUserId(protectedDeal.getProviderId()).get();
        providerAccount.increasePoint(protectedDeal.getDeposit());
        Home home = OptionalUtil.getOrElseThrow(homeRepository.findById(protectedDeal.getHomeId()), NOT_EXIST_HOME_ID);
        home.setHomeStatus(HomeStatus.SOLD_OUT);
        protectedDeal.getProtectedDealDateTime().setCompleteAt(LocalDateTime.now());
        protectedDeal.setDealState(DealState.COMPLETE_DEAL);
        sendCompleteFCM(provider.getFcmToken());
    }

    /**
     * 안전 거래 생성 전 취소 메서드
     */
    @Transactional
    public void cancelBeforeDeal(Long dealId) {
        ProtectedDeal protectedDeal = OptionalUtil.getOrElseThrow(protectedDealRepository.findById(dealId), DEAL_NOT_FOUND);
        Home home = OptionalUtil.getOrElseThrow(homeRepository.findById(protectedDeal.getHomeId()), NOT_EXIST_HOME_ID);
        home.setHomeStatus(HomeStatus.FOR_SALE);
        protectedDeal.getProtectedDealDateTime().setCancelAt(LocalDateTime.now());
        protectedDeal.setDealState(DealState.CANCEL_BEFORE_DEAL);
    }

    /**
     * 안전 거래 생성 후 취소 메서드 (by getter)
     */
    @Transactional
    public void cancelAfterDeal(Long dealId) {
        ProtectedDeal protectedDeal = OptionalUtil.getOrElseThrow(protectedDealRepository.findById(dealId), DEAL_NOT_FOUND);
        UserAccount getterAccount = userAccountRepository.findByUserId(protectedDeal.getGetterId()).get();
        getterAccount.increasePoint(protectedDeal.getDeposit());
        Home home = OptionalUtil.getOrElseThrow(homeRepository.findById(protectedDeal.getHomeId()), NOT_EXIST_HOME_ID);
        home.setHomeStatus(HomeStatus.FOR_SALE);
        protectedDeal.getProtectedDealDateTime().setCancelAt(LocalDateTime.now());
        protectedDeal.setDealState(DealState.CANCEL_DURING_DEAL);
    }



    private void sendCompleteFCM(String fcmToken) {
        fcmHelper.sendNotification(FCMState.SAVE, fcmToken, "The transaction has been completed", "the deposit has been paid. Please check it on MyPage.");
    }
}
