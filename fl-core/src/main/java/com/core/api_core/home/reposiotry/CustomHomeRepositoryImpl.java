package com.core.api_core.home.reposiotry;


import com.core.api_core.home.model.Home;
import com.core.api_core.home.model.HomeStatus;
import com.core.api_core.home.model.QHome;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomHomeRepositoryImpl implements CustomHomeRepository {

    private final JPAQueryFactory query;
    private final QHome qHome = QHome.home;

    @Override
    public List<Home> findByFilter() {
        return null;
    }

    @Override
    public List<Home> findByAddress() {
        return null;
    }

    @Override
    public List<Home> findByPostCode(Integer postCode) {
        return query.selectFrom(qHome)
                .where(qHome.homeAddress.postCode.like("%" + postCode + "%"))
                .fetch();
    }

    @Override
    public List<Home> findAllSellHome() {
        return query.selectFrom(qHome)
                .where(qHome.homeStatus.eq(HomeStatus.FOR_SALE))
                .fetch();
    }

    @Override
    public List<Home> findByCity(String cityName) {
        List<Home> homes = query.selectFrom(qHome)
                .where(qHome.homeAddress.city.like("%" + cityName +"%"))
                .fetch();

        return homes;
    }

    @Override
    public List<Home> findByUserIdx(Long userIdx) {
        List<Home> homes = query.selectFrom(qHome)
                .where(qHome.userIdx.eq(userIdx))
                .fetch();

        return homes;
    }

    @Override
    public List<Home> findByUserIds(Long user1Id, Long user2Id) {
        return query.selectFrom(qHome)
                .where(qHome.userIdx.eq(user1Id).or(qHome.userIdx.eq(user2Id)))
                .fetch();
    }


//    @Override
//    public Optional<Home> findByIdWithUser(Long id) {
//        return null;
//    }
}