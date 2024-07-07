package com.core.api_core.deal.repository;

import com.core.api_core.deal.model.ProtectedDeal;

import java.util.Optional;

public interface CustomProtectedDealRepository {

    Optional<ProtectedDeal> findByUserId(Long getterId, Long providerId);
}
