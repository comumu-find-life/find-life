package com.core.home.reposiotry;


import com.core.home.model.Home;
import com.core.home.model.QHome;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

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
    public List<Home> findByCity(String cityName) {
        List<Home> homes = query.selectFrom(qHome)
                .where(qHome.homeAddress.city.like("%" + cityName +"%"))
                .fetch();

        return homes;
    }


    @Override
    public Optional<Home> findByIdWithUser(Long id) {
        return null;
    }
}