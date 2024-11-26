package com.core.api_core.deal.repository;

import com.core.api_core.deal.model.DealState;
import com.core.api_core.deal.model.ProtectedDeal;
import com.core.api_core.deal.model.QProtectedDeal;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CustomProtectedDealRepositoryImpl implements CustomProtectedDealRepository {

    private final JPAQueryFactory query;
    private final QProtectedDeal qProtectedDeal = QProtectedDeal.protectedDeal;

    @Override
    public List<ProtectedDeal> findAllByUserId(Long userId) {
        return query.selectFrom(qProtectedDeal)
                .where(qProtectedDeal.getterId.eq(userId)
                        .or(qProtectedDeal.providerId.eq(userId)))
                .fetch();
    }

    @Override
    public List<ProtectedDeal> findByMultipleParams(Long getterId, Long providerId, Long homeId, Long dmId) {
        return query.selectFrom(qProtectedDeal)
                .where(qProtectedDeal.getterId.eq(getterId),
                        qProtectedDeal.providerId.eq(providerId),
                        qProtectedDeal.homeId.eq(homeId),
                        qProtectedDeal.dmId.eq(dmId))
                .fetch();
    }

    @Override
    public List<ProtectedDeal> findAllRequestDeposit() {
        return query.selectFrom(qProtectedDeal)
                .where(qProtectedDeal.dealState.eq(DealState.REQUEST_DEAL))
                .fetch();
    }

    @Override
    public List<ProtectedDeal> findAllSubmitDeal() {
        return query.selectFrom(qProtectedDeal)
                .where(qProtectedDeal.dealState.eq(DealState.ACCEPT_DEAL))
                .fetch();
    }
}
