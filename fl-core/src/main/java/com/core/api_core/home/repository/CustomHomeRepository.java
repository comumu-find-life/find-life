package com.core.api_core.home.repository;

import com.core.api_core.home.dto.HomeInformationResponse;
import com.core.api_core.home.model.Home;
import com.querydsl.core.Tuple;

import java.util.List;
import java.util.Optional;

public interface CustomHomeRepository {

    List<Tuple> findAllSellHome();

    List<Home> findByCity(String cityName);

    List<Home> findByUserId(Long userId);

    Optional<HomeInformationResponse> findHomeAndUserById(Long homeId);

    List<Tuple> findFavoriteHomes(final List<Long> homeIds);
}