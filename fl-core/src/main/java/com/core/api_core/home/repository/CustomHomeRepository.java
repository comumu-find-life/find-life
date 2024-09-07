package com.core.api_core.home.repository;

import com.core.api_core.home.model.Home;

import java.util.List;

public interface CustomHomeRepository {

    List<Home> findAllSellHome();

    List<Home> findByCity(String cityName);

    List<Home> findByUserId(Long userId);

}