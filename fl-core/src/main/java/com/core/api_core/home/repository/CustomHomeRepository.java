package com.core.api_core.home.repository;

import com.core.api_core.home.dto.HomeInformationResponse;
import com.core.api_core.home.dto.HomeOverviewResponse;
import com.core.api_core.home.model.Home;
import com.querydsl.core.Tuple;

import java.util.List;
import java.util.Optional;

public interface CustomHomeRepository {

    List<HomeOverviewResponse> findAllSellHome();

    List<Home> findByCity(final String cityName);

    List<Home> findByUserId(final Long userId);

    Optional<HomeInformationResponse> findHomeAndUserById(final Long homeId);

    List<HomeOverviewResponse> findFavoriteHomes(final List<Long> homeIds);
}