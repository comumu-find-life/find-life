package com.redis.home;

import com.core.api_core.home.model.Home;

public interface HomeRedisService {
    Home findById(Long id);
}
