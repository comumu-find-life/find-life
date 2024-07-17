package com.core.api_core.home.reposiotry;

import com.core.api_core.home.model.Home;

import java.util.List;

public interface CustomHomeRepository {
    //검색 조건에 맞춘 동적 검색
    List<Home> findByFilter();

    //주소를 기반으로 찾기
    List<Home> findByAddress();

    //postcode 로 검색
    List<Home> findByPostCode(Integer postCode);

    List<Home> findAllSellHome();

    //시티이름 기반으로 찾기
    List<Home> findByCity(String cityName);

    List<Home> findByUserIdx(Long userIdx);

    List<Home> findByUserIds(Long user1Id, Long user2Id);

    // Id로 찾기. 유저정보와 같이
//    @Query("SELECT h FROM Home h JOIN FETCH h.user WHERE h.id = :id")
//    Optional<Home> findByIdWithUser(Long id);
}