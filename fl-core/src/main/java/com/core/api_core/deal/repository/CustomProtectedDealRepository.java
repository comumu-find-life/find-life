package com.core.api_core.deal.repository;

import com.core.api_core.deal.model.ProtectedDeal;

import java.util.List;
import java.util.Optional;

public interface CustomProtectedDealRepository {


    List<ProtectedDeal> findAllByUserId(Long userId);

    List<ProtectedDeal> findByMultipleParams(Long getterId, Long providerId, Long homeId, Long dmId);

    List<ProtectedDeal> findAllBeforeDeposit();

    /**
     * 입금 신청 상태의 안전거래 모두 조회
     */
    List<ProtectedDeal> findAllSubmitDeal();
}
