package com.core.home.reposiotry;

import com.core.home.model.Home;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface CustomHomeRepository {
    //검색 조건에 맞춘 동적 검색
    List<Home> findByFilter();

    //주소를 기반으로 찾기
    List<Home> findByAddress();

    //postcode 로 검색
    List<Home> findByPostCode(Integer postCode);

    //시티이름 기반으로 찾기
    List<Home> findByCity(String cityName);

    List<Home> findByUserIdx(Long userIdx);

    // Id로 찾기. 유저정보와 같이
//    @Query("SELECT h FROM Home h JOIN FETCH h.user WHERE h.id = :id")
//    Optional<Home> findByIdWithUser(Long id);
}