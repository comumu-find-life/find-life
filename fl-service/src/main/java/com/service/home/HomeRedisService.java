package com.service.home;

import com.core.home.reposiotry.HomeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class HomeRedisService {

    private final HomeRepository homeRepository;

    //todo 인기 게시글은 캐싱 조회

    //todo 찜 목록은 캐싱 조회


}
