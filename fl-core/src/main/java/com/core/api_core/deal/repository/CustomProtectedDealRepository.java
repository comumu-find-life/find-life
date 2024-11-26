package com.core.api_core.deal.repository;

import com.core.api_core.deal.model.ProtectedDeal;

import java.util.List;

public interface CustomProtectedDealRepository {

    List<ProtectedDeal> findAllByUserId(Long userId);

    List<ProtectedDeal> findByMultipleParams(Long getterId, Long providerId, Long homeId, Long dmId);

    List<ProtectedDeal> findAllRequestDeposit();

    List<ProtectedDeal> findAllSubmitDeal();
}
