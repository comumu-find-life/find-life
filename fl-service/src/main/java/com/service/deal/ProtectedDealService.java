package com.service.deal;

import com.common.chat.request.DirectMessageRequest;
import com.common.deal.mapper.ProtectedDealMapper;
import com.common.deal.request.ProtectedDealFindRequest;
import com.common.deal.request.ProtectedDealGeneratorRequest;
import com.common.deal.response.ProtectedDealResponse;
import com.core.api_core.deal.model.DealState;
import com.core.api_core.deal.model.ProtectedDeal;
import com.core.api_core.deal.repository.ProtectedDealRepository;
import com.core.api_core.user.repository.UserRepository;
import com.service.utils.OptionalUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class ProtectedDealService {

    private final ProtectedDealRepository protectedDealRepository;
    private final ProtectedDealMapper mapper;

    /**
     * 안전 거래 생성 메서드
     */
    public void save(ProtectedDealGeneratorRequest request) {
        ProtectedDeal deal = mapper.toEntity(request);
        protectedDealRepository.save(deal);
    }

    /**
     * 내 안전거래 조회 메서드
     */
    public List<ProtectedDealResponse> findAllByUserId(Long userId) {
        List<ProtectedDeal> allByUserId = protectedDealRepository.findAllByUserId(userId);
        return allByUserId.stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }


    /**
     * 안전거래 조회 메서드
     */
    public ProtectedDealResponse findByDealInformation(ProtectedDealFindRequest request) {
        Optional<ProtectedDeal> byMultipleParams = protectedDealRepository.findByMultipleParams(request.getGetterId(), request.getProviderId(), request.getHomeId(), request.getDmId());
        if (byMultipleParams.isEmpty()) {
            return null;
        }
        return mapper.toResponse(byMultipleParams.get());
    }

    /**
     * 입금 신청 메서드 by getter
     */
    @Transactional
    public void requestDeposit(Long dealId) {
        //todo cms 에서 확인 요청
        ProtectedDeal protectedDeal = OptionalUtil.getOrElseThrow(protectedDealRepository.findById(dealId), "존재하지 않는 거래 ID 입니다.");
        protectedDeal.setDealState(DealState.DURING_DEPOSIT);

    }

    /**
     * 입금 완료 매서드 by admin
     */
    @Transactional
    public void doneDeposit(Long dealId) {
        ProtectedDeal protectedDeal = OptionalUtil.getOrElseThrow(protectedDealRepository.findById(dealId), "ProtectedDeal not found with id");
        protectedDeal.setDealState(DealState.DONE_DEPOSIT);
    }

    /**
     * 입금 완료 실패 메서드 by admin
     */
    @Transactional
    public void failDeposit(Long dealId) {
        ProtectedDeal protectedDeal = OptionalUtil.getOrElseThrow(protectedDealRepository.findById(dealId), "ProtectedDeal not found with id");
        protectedDeal.setDealState(DealState.BEFORE_DEPOSIT);
    }

    /**
     * 거래 완료 메서드 by getter
     */
    @Transactional
    public void finishDeal(Long dealId) {
        ProtectedDeal protectedDeal = OptionalUtil.getOrElseThrow(protectedDealRepository.findById(dealId), "ProtectedDeal not found with id");
        //todo cms 에서 입금하는 로직 구현
        protectedDeal.setDealState(DealState.FINISH);
    }


    @Transactional
    public void cancelDeal(Long dealId) {
        ProtectedDeal protectedDeal = OptionalUtil.getOrElseThrow(protectedDealRepository.findById(dealId), "ProtectedDeal not found with id");
        //todo cms 에서 입금하는 로직 구현
        protectedDeal.setDealState(DealState.CANCEL);
    }

}
