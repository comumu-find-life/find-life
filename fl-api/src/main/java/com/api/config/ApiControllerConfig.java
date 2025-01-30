package com.api.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@EnableCaching
@EnableJpaAuditing
@Configuration
@ComponentScan(basePackages = {"com.api","com.service", "com.core", "com.redis", "com.chatting", "com.infra"})
@EnableRedisRepositories(basePackages = "com.redis.user.repository")
public class ApiControllerConfig {
}
