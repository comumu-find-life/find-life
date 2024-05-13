package com.redis.home;

import com.core.home.model.Home;

public interface HomeRedisService {
    Home findById(Long id);
}
