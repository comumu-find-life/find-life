package com.core.api_core.deal.repository;

import com.core.api_core.deal.model.ProtectedDeal;

import java.util.List;
import java.util.Optional;

public interface CustomProtectedDealRepository {

    Optional<ProtectedDeal> findByUserId(Long getterId, Long providerId);

    List<ProtectedDeal> findAllByUserId(Long userId);

    Optional<ProtectedDeal> findByMultipleParams(Long getterId, Long providerId, Long homeId, Long dmId);

    List<ProtectedDeal> findAllBeforeDeposit();
}
