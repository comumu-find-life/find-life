package com.service.deal;

import com.common.deal.mapper.ProtectedDealMapper;
import com.common.deal.response.ProtectedDealAdminResponse;
import com.common.deal.response.ProtectedDealOverViewResponse;
import com.common.home.mapper.HomeMapper;
import com.common.home.response.HomeInformationResponse;
import com.common.user.mapper.UserMapper;
import com.common.user.response.UserInformationByAdminResponse;
import com.core.api_core.deal.model.DealState;
import com.core.api_core.deal.model.ProtectedDeal;
import com.core.api_core.deal.repository.ProtectedDealRepository;
import com.core.api_core.home.model.Home;
import com.core.api_core.home.repository.HomeRepository;
import com.core.api_core.user.model.User;
import com.core.api_core.user.repository.UserRepository;
import com.common.utils.OptionalUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.service.deal.ProtectedDealMessages.DEAL_NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
public class ProtectedDealAdminService {

    private final ProtectedDealMapper protectedDealMapper;
    private final HomeMapper homeMapper;
    private final UserMapper userMapper;
    private final HomeRepository homeRepository;
    private final UserRepository userRepository;
    private final ProtectedDealRepository dealRepository;

    /**
     * 입금 신청된 모든 안전 거래 조회 메서드
     */
    public List<ProtectedDealOverViewResponse> findAllRequestDeposit() {
        List<ProtectedDeal> allBeforeDeposit = dealRepository.findAllRequestDeposit();
        List<ProtectedDealOverViewResponse> responses = new ArrayList<>();
        allBeforeDeposit.forEach(deal -> responses.add(protectedDealMapper.toAdminOverViewResponse(deal)));
        return responses;
    }

    /**
     * 안전 거래 단일 조회 메서드
     */
    public ProtectedDealAdminResponse findById(Long dealId) {
        ProtectedDeal protectedDeal = OptionalUtil.getOrElseThrow(dealRepository.findById(dealId), ProtectedDealMessages.DEAL_NOT_FOUND);
        return mapToProtectedDealAdminResponse(protectedDeal);
    }

    /**
     * 거래 수락 메서드 (By Getter)
     */
    public void checkDeposit(Long dealId) {
        ProtectedDeal protectedDeal = OptionalUtil.getOrElseThrow(dealRepository.findById(dealId), ProtectedDealMessages.DEAL_NOT_FOUND);
        protectedDeal.setDealState(DealState.ACCEPT_DEAL);
    }

    /**
     * 입금 취소 메서드
     */
    public void cancelDeposit(Long dealId){
        ProtectedDeal protectedDeal = OptionalUtil.getOrElseThrow(dealRepository.findById(dealId), ProtectedDealMessages.DEAL_NOT_FOUND);
        protectedDeal.setDealState(DealState.CANCEL_BEFORE_DEAL);
    }

    /**
     * 거래 확정 요청된 모든 안전거래 조회 메서드
     */
    public List<ProtectedDealOverViewResponse> findAllSubmitDeal() {
        List<ProtectedDeal> allSubmitDeal = dealRepository.findAllSubmitDeal();
        List<ProtectedDealOverViewResponse> responses = new ArrayList<>();
        allSubmitDeal.forEach(deal -> responses.add(protectedDealMapper.toAdminOverViewResponse(deal)));
        return responses;
    }

    /**
     * 거래 확정 메서드
     */
    public void completeDeal(Long dealId) {
        ProtectedDeal protectedDeal = OptionalUtil.getOrElseThrow(dealRepository.findById(dealId), DEAL_NOT_FOUND);
        //todo cms 에서 입금하는 로직 구현
        protectedDeal.setDealState(DealState.COMPLETE_DEAL);
    }

    private ProtectedDealAdminResponse mapToProtectedDealAdminResponse(ProtectedDeal deal) {
        User provider = findUserById(deal.getProviderId(), ProtectedDealMessages.USER_NOT_FOUND);
        User getter = findUserById(deal.getGetterId(), ProtectedDealMessages.USER_NOT_FOUND);
        Home home = findHomeById(deal.getHomeId(), ProtectedDealMessages.USER_NOT_FOUND);

        HomeInformationResponse homeInfoResponse = homeMapper.toHomeInformation(home, provider);
        UserInformationByAdminResponse providerResponse = userMapper.toAdminResponse(provider);
        UserInformationByAdminResponse getterResponse = userMapper.toAdminResponse(getter);

        return ProtectedDealAdminResponse.builder()
                .id(deal.getId())
                .dmId(deal.getDmId())
                .homeInformationResponse(homeInfoResponse)
                .getter(getterResponse)
                .provider(providerResponse)
                .deposit(deal.getDeposit())
                .build();
    }

    private User findUserById(Long userId, String errorMessage) {
        return OptionalUtil.getOrElseThrow(userRepository.findById(userId), errorMessage);
    }

    private Home findHomeById(Long homeId, String errorMessage) {
        return OptionalUtil.getOrElseThrow(homeRepository.findById(homeId), errorMessage);
    }
}
