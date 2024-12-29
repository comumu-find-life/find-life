package com.core.api_core.home.repository;


import com.core.api_core.home.model.Home;
import com.core.api_core.home.model.HomeStatus;
import com.core.api_core.home.model.QHome;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomHomeRepositoryImpl implements com.core.api_core.home.repository.CustomHomeRepository {

    private final JPAQueryFactory query;
    private final QHome qHome = QHome.home;


    @Override
    public List<Home> findAllSellHome() {
        return query.selectFrom(qHome)
                .where(qHome.homeStatus.eq(HomeStatus.FOR_SALE))
                .fetch();
    }

    @Override
    public List<Home> findByCity(String cityName) {
        List<Home> homes = query.selectFrom(qHome)
                .where(qHome.homeStatus.eq(HomeStatus.FOR_SALE))
                .where(qHome.homeAddress.city.like("%" + cityName +"%"))
                .fetch();

        return homes;
    }

    @Override
    public List<Home> findByUserId(Long userIdx) {
        List<Home> homes = query.selectFrom(qHome)
                .where(qHome.userIdx.eq(userIdx))
                .fetch();
        return homes;
    }


}