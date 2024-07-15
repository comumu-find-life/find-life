package com.core.api_core.deal.repository;

import com.core.api_core.deal.model.ProtectedDeal;
import com.core.api_core.deal.model.QProtectedDeal;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CustomProtectedDealRepositoryImpl implements CustomProtectedDealRepository {

    private final JPAQueryFactory query;
    private final QProtectedDeal qProtectedDeal = QProtectedDeal.protectedDeal;

    //todo getterId 와 provider 로 구분할 수 있는 안전거래 객체는 무조건 한개인가 ?
    @Override
    public Optional<ProtectedDeal> findByUserId(Long getterId, Long providerId) {
        return Optional.ofNullable(query.selectFrom(qProtectedDeal)
                .where(qProtectedDeal.getterId.eq(getterId),
                        qProtectedDeal.providerId.eq(providerId))
                .fetchOne());
    }

    @Override
    public Optional<ProtectedDeal> findByMultipleParams(Long getterId, Long providerId, Long homeId, Long dmId) {
        return Optional.ofNullable(query.selectFrom(qProtectedDeal)
                .where(qProtectedDeal.getterId.eq(getterId),
                        qProtectedDeal.providerId.eq(providerId),
                        qProtectedDeal.homeId.eq(homeId),
                        qProtectedDeal.dmId.eq(dmId))
                .fetchFirst());  // fetchFirst()를 사용하여 첫 번째 결과만 반환
    }
}
