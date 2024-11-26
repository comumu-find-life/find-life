package com.chatting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.common", "com.core.api_core.user.model", "com.chatting"})
@EnableJpaRepositories(basePackages = {"com.core.api_core.chat.repository", "com.core.api_core.user.repository"})
@EnableMongoRepositories(basePackages = {"com.core.api_core.chat.model", "com.core.api_core.chat.repository"})
@EntityScan(basePackages = { "com.core.chat_core.chat.model", "com.core.api_core.chat.model", "com.core.api_core.user.model"})
public class ChattingApplication {


    public static void main(String[] args) {
        SpringApplication.run(ChattingApplication.class, args);
    }


}
