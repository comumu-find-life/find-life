package com.service.deal;

import com.common.deal.mapper.ProtectedDealMapper;
import com.common.deal.request.ProtectedDealFindRequest;
import com.common.deal.request.ProtectedDealGeneratorRequest;
import com.common.deal.response.MyProtectedDealResponse;
import com.common.deal.response.ProtectedDealByGetterResponse;
import com.common.deal.response.ProtectedDealByProviderResponse;
import com.core.api_core.deal.model.DealState;
import com.core.api_core.deal.model.ProtectedDeal;
import com.core.api_core.deal.repository.ProtectedDealRepository;
import com.core.api_core.home.model.Home;
import com.core.api_core.home.reposiotry.HomeRepository;
import com.service.utils.OptionalUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.service.deal.ProtectedDealMessages.DEAL_NOT_FOUND;
import static com.service.deal.ProtectedDealMessages.NOT_EXIST_DEAL_INFORMATION;
import static com.service.home.HomeMessages.NOT_EXIST_HOME_ID;

@Transactional
@Service
@RequiredArgsConstructor
public class ProtectedDealService {

    private final ProtectedDealRepository protectedDealRepository;
    private final HomeRepository homeRepository;
    private final ProtectedDealMapper mapper;

    /**
     * 안전 거래 생성 메서드
     */
    public void save(ProtectedDealGeneratorRequest request) {
        ProtectedDeal deal = mapper.toEntity(request, generateRandomUUID());
        protectedDealRepository.save(deal);
    }

    /**
     * 내 안전거래 조회 메서드
     */
    public List<MyProtectedDealResponse> findAllByUserId(Long userId) {
        List<ProtectedDeal> allByUserId = protectedDealRepository.findAllByUserId(userId);
        List<MyProtectedDealResponse> response = new ArrayList<>();
        allByUserId.stream()
                .forEach(protectedDeal -> {
                    Home home = OptionalUtil.getOrElseThrow(homeRepository.findById(protectedDeal.getHomeId()), "존재하지 않는 집 ID 입니다.");
                    response.add(mapper.toResponseV2(protectedDeal, home));
                });
        return response;
    }


    /**
     * 안전거래 조회 메서드 by Getter
     */
    public ProtectedDealByGetterResponse findByGetterDealInformation(ProtectedDealFindRequest request) {
        ProtectedDeal protectedDeal = OptionalUtil.getOrElseThrow(protectedDealRepository.findByMultipleParams(request.getGetterId(), request.getProviderId(), request.getHomeId(), request.getDmId()), NOT_EXIST_DEAL_INFORMATION);
        Home home = OptionalUtil.getOrElseThrow(homeRepository.findById(request.getHomeId()), NOT_EXIST_HOME_ID);
        //todo getter 와 provider 판단 로직
        return mapper.toGetterResponse(protectedDeal, home);
    }

    /**
     * 안전거래 조회 메서드 by Provider
     */
    public ProtectedDealByProviderResponse findByProviderDealInformation(ProtectedDealFindRequest request) {
        ProtectedDeal protectedDeal = OptionalUtil.getOrElseThrow(protectedDealRepository.findByMultipleParams(request.getGetterId(), request.getProviderId(), request.getHomeId(), request.getDmId()), NOT_EXIST_DEAL_INFORMATION);
        Home home = OptionalUtil.getOrElseThrow(homeRepository.findById(request.getHomeId()), NOT_EXIST_HOME_ID);
        return mapper.toProviderResponse(protectedDeal, home);
    }

    /**
     * 입금 신청 메서드 by getter
     */
    @Transactional
    public void requestDeposit(Long dealId) {
        //todo cms 에서 확인 요청
        ProtectedDeal protectedDeal = OptionalUtil.getOrElseThrow(protectedDealRepository.findById(dealId), DEAL_NOT_FOUND);
        protectedDeal.setDealState(DealState.DURING_DEPOSIT);

    }

    /**
     * 입금 완료 매서드 by admin
     */
    @Transactional
    public void doneDeposit(Long dealId) {
        ProtectedDeal protectedDeal = OptionalUtil.getOrElseThrow(protectedDealRepository.findById(dealId), DEAL_NOT_FOUND);
        protectedDeal.setDealState(DealState.DONE_DEPOSIT);
    }

    /**
     * 입금 완료 실패 메서드 by admin
     */
    @Transactional
    public void failDeposit(Long dealId) {
        ProtectedDeal protectedDeal = OptionalUtil.getOrElseThrow(protectedDealRepository.findById(dealId), DEAL_NOT_FOUND);
        protectedDeal.setDealState(DealState.BEFORE_DEPOSIT);
    }

    /**
     * 거래 완료 메서드 by getter
     */
    @Transactional
    public void finishDeal(Long dealId) {
        ProtectedDeal protectedDeal = OptionalUtil.getOrElseThrow(protectedDealRepository.findById(dealId), DEAL_NOT_FOUND);
        //todo cms 에서 입금하는 로직 구현
        protectedDeal.setDealState(DealState.FINISH);
    }


    @Transactional
    public void cancelDeal(Long dealId) {
        ProtectedDeal protectedDeal = OptionalUtil.getOrElseThrow(protectedDealRepository.findById(dealId), DEAL_NOT_FOUND);
        //todo cms 에서 입금하는 로직 구현
        protectedDeal.setDealState(DealState.CANCEL);
    }

    //랜덤 UUID 생성   TODO 로직 변경
    private String generateRandomUUID() {
        return UUID.randomUUID().toString();
    }

}
