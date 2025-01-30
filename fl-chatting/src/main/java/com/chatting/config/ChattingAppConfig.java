package com.chatting.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@ComponentScan(basePackages = {"com.infra", "com.core"})
@EnableJpaRepositories(basePackages = {"com.core"})
@EnableMongoRepositories(basePackages = {"com.core.domain"})
@EntityScan(basePackages = {"com.core"})
public class ChattingAppConfig {
}
