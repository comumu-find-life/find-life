package com.core.api_core.deal.repository;

import com.core.api_core.deal.model.ProtectedDeal;
import com.querydsl.core.Tuple;

import java.util.List;

public interface CustomProtectedDealRepository {

    List<ProtectedDeal> findAllByUserId(Long userId);

    List<Tuple> findByMultipleParams(Long getterId, Long providerId, Long homeId, Long dmId);

    List<ProtectedDeal> findAllRequestDeposit();

    List<ProtectedDeal> findAllSubmitDeal();
}
