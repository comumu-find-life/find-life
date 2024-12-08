package com.redis;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.core.api_core.user.repository.UserRepository")
public class RedisApplication {
}
