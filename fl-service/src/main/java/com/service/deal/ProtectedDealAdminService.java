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
        ProtectedDeal protectedDeal = OptionalUtil.getOrElseThrow(dealRepository.findById(dealId), ProtectedDealMessages.DEAL_NOT_FOUND);
        return mapToProtectedDealAdminResponse(protectedDeal);
    }

    public void checkDeposit(Long dealId) {
        ProtectedDeal protectedDeal = OptionalUtil.getOrElseThrow(dealRepository.findById(dealId), ProtectedDealMessages.DEAL_NOT_FOUND);
        protectedDeal.setDealState(DealState.DONE_DEPOSIT);
    }

    public List<ProtectedDealOverViewResponse> findAllSubmitDeal() {
        List<ProtectedDeal> allSubmitDeal = dealRepository.findAllSubmitDeal();
        List<ProtectedDealOverViewResponse> responses = new ArrayList<>();
        allSubmitDeal.forEach(deal -> responses.add(protectedDealMapper.toAdminOverViewResponse(deal)));
        return responses;
    }

    public void completeDeal(Long dealId) {
        ProtectedDeal protectedDeal = OptionalUtil.getOrElseThrow(dealRepository.findById(dealId), ProtectedDealMessages.DEAL_NOT_FOUND);
        protectedDeal.setDealState(DealState.FINISH);
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
