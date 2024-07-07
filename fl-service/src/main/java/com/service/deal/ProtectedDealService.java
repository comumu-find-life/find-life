package com.service.deal;

import com.common.deal.mapper.ProtectedDealMapper;
import com.common.deal.request.ProtectedDealGeneratorRequest;
import com.core.api_core.deal.model.DealState;
import com.core.api_core.deal.model.ProtectedDeal;
import com.core.api_core.deal.repository.ProtectedDealRepository;
import com.core.api_core.user.model.User;
import com.core.api_core.user.repository.UserRepository;
import com.service.deal.dto.ProtectedDealResponse;
import com.service.utils.OptionalUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class ProtectedDealService {

    private final UserRepository userRepository;
    private final ProtectedDealRepository protectedDealRepository;
    private final ProtectedDealMapper mapper;

    /**
     * 안전 거래 생성 메서드
     */
    public void save(ProtectedDealGeneratorRequest request) {
        ProtectedDeal deal = mapper.toEntity(request);
        //todo DirectMessage 에도 추가.
        protectedDealRepository.save(deal);
    }

    /**
     * 입금 신청 메서드 by getter
     */
    @Transactional
    public void requestDeposit(Long dealId){
        //todo cms 에서 확인 요청
    }

    /**
     * 입금 완료 매서드 by admin
     */
    @Transactional
    public void doneDeposit(Long dealId){
        ProtectedDeal protectedDeal = OptionalUtil.getOrElseThrow(protectedDealRepository.findById(dealId), "ProtectedDeal not found with id");
        protectedDeal.setDealState(DealState.DONE_DEPOSIT);
    }

    /**
     * 입금 완료 실패 메서드 by admin
     */
    @Transactional
    public void failDeposit(Long dealId){
        ProtectedDeal protectedDeal = OptionalUtil.getOrElseThrow(protectedDealRepository.findById(dealId), "ProtectedDeal not found with id");
        protectedDeal.setDealState(DealState.BEFORE_DEPOSIT);
    }

    /**
     * 거래 완료 메서드 by getter
     */
    @Transactional
    public void finishDeal(Long dealId){
        ProtectedDeal protectedDeal = OptionalUtil.getOrElseThrow(protectedDealRepository.findById(dealId), "ProtectedDeal not found with id");
        protectedDeal.setDealState(DealState.FINISH);
        //todo cms 에서 입금하는 로직 구현
    }

}
