package com.service.deal;

import com.common.deal.mapper.ProtectedDealMapper;
import com.common.deal.response.ProtectedDealAdminResponse;
import com.common.home.mapper.HomeMapper;
import com.common.home.response.HomeInformationResponse;
import com.common.user.mapper.UserMapper;
import com.common.user.response.UserInformationByAdminResponse;
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

    public List<ProtectedDealAdminResponse> findAllBeforeDeposit() {
        List<ProtectedDealAdminResponse> responses = new ArrayList<>();
        List<ProtectedDeal> allBeforeDeposit = dealRepository.findAllBeforeDeposit();
        allBeforeDeposit.stream()
                .forEach(protectedDeal -> {
                    User provider = OptionalUtil.getOrElseThrow(userRepository.findById(protectedDeal.getProviderId()), "존재하지 않는 유저 ID 입니다.");
                    HomeInformationResponse homeInformationResponse = homeMapper.toHomeInformation(OptionalUtil.getOrElseThrow(homeRepository.findById(protectedDeal.getHomeId()), "존재하지 않는 집 ID 입니다."), provider);
                    UserInformationByAdminResponse providerResponse = userMapper.toAdminResponse(provider);
                    UserInformationByAdminResponse getterResponse = userMapper.toAdminResponse(OptionalUtil.getOrElseThrow(userRepository.findById(protectedDeal.getGetterId()), "존재하지 않는 유저 ID 입니다."));
                    ProtectedDealAdminResponse response = ProtectedDealAdminResponse.builder()
                            .id(protectedDeal.getId())
                            .homeInformationResponse(homeInformationResponse)
                            .getter(getterResponse)
                            .provider(providerResponse)
                            .deposit(protectedDeal.getDeposit())
                            .build();
                    responses.add(response);
                });
        return responses;
    }


}
