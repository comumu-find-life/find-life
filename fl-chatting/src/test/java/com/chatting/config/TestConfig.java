package com.chatting.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@TestConfiguration
@Configuration
@ComponentScan(basePackages = {"com.chatting", "com.core", "com.common", "com.service"})
public class TestConfig {

}
