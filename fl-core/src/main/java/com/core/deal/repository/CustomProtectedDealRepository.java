package com.core.deal.repository;

import com.core.deal.model.ProtectedDeal;

import java.util.Optional;

public interface CustomProtectedDealRepository {

    Optional<ProtectedDeal> findByUserId(Long getterId, Long providerId);
}
