package com.chatting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.TimeZone;

/**
 *
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.infra", "com.core", "com.chatting"})
@EnableJpaRepositories(basePackages = {"com.core.domain.chat.repository", "com.core.domain.user.repository"})
@EnableMongoRepositories(basePackages = {"com.core.domain.chat.model", "com.core.domain.chat.repository"})
@EntityScan(basePackages = { "com.core.chat_core.chat.model", "com.core.domain.chat.model", "com.core.domain.user.model"})
public class ChattingApplication {
    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("Australia/Sydney"));
        SpringApplication.run(ChattingApplication.class, args);
    }
}
