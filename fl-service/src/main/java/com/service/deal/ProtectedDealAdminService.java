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
import com.core.api_core.home.reposiotry.HomeRepository;
import com.core.api_core.user.model.User;
import com.core.api_core.user.repository.UserRepository;
import com.service.utils.OptionalUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public List<ProtectedDealOverViewResponse> findAllBeforeDeposit() {
        List<ProtectedDeal> allBeforeDeposit = dealRepository.findAllBeforeDeposit();
        List<ProtectedDealOverViewResponse> responses = new ArrayList<>();
        allBeforeDeposit.forEach(deal -> responses.add(protectedDealMapper.toAdminOverViewResponse(deal)));
        return responses;
    }

    public ProtectedDealAdminResponse findById(Long dealId) {
        ProtectedDeal protectedDeal = OptionalUtil.getOrElseThrow(dealRepository.findById(dealId), "존재하지 않는 안전거래 ID 입니다.");
        return mapToProtectedDealAdminResponse(protectedDeal);
    }

    public void checkDeposit(Long dealId) {
        ProtectedDeal protectedDeal = OptionalUtil.getOrElseThrow(dealRepository.findById(dealId), "존재하지 않는 안전거래 ID 입니다.");
        protectedDeal.setDealState(DealState.DONE_DEPOSIT);
    }

    /**
     * 거래 완료 신청된 모든 안전거래 조회 로직
     */
    public List<ProtectedDealOverViewResponse>  findAllSubmitDeal(){
        List<ProtectedDeal> allSubmitDeal = dealRepository.findAllSubmitDeal();
        List<ProtectedDealOverViewResponse> responses = new ArrayList<>();
        allSubmitDeal.forEach(deal -> responses.add(protectedDealMapper.toAdminOverViewResponse(deal)));
        return responses;
    }

    public void completeDeal(Long dealId){
        ProtectedDeal protectedDeal = OptionalUtil.getOrElseThrow(dealRepository.findById(dealId), "존재하지 않는 안전거래 ID 입니다.");
        protectedDeal.setDealState(DealState.FINISH);
    }


    private ProtectedDealAdminResponse mapToProtectedDealAdminResponse(ProtectedDeal deal) {
        User provider = findUserById(deal.getProviderId(), "존재하지 않는 유저 ID 입니다.");
        User getter = findUserById(deal.getGetterId(), "존재하지 않는 유저 ID 입니다.");
        Home home = findHomeById(deal.getHomeId(), "존재하지 않는 집 ID 입니다.");

        HomeInformationResponse homeInfoResponse = homeMapper.toHomeInformation(home, provider);
        UserInformationByAdminResponse providerResponse = userMapper.toAdminResponse(provider);
        UserInformationByAdminResponse getterResponse = userMapper.toAdminResponse(getter);

        return ProtectedDealAdminResponse.builder()
                .id(deal.getId())
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
