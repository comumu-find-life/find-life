package com.api.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@EnableCaching
@EnableJpaAuditing
@Configuration
@EntityScan(basePackages = {"com.core"})
@EnableJpaRepositories(basePackages = {"com.core"})
@ComponentScan(basePackages = {"com.api", "com.core", "com.chatting", "com.infra"})
@EnableRedisRepositories(basePackages = "com.redis.user.repository")
public class ApiControllerConfig {
}
