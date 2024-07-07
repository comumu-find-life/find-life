package com.redis.home.impl;

import com.core.api_core.home.model.Home;
import com.core.api_core.home.reposiotry.HomeRepository;
import com.redis.home.HomeRedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class HomeRedisServiceImpl implements HomeRedisService {

    private final HomeRepository homeRepository;


    @Override
    public Home findById(Long id) {
        return null;
    }
}
