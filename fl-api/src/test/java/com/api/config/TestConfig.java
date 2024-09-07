package com.api.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@ComponentScan(basePackages = {"com.api", "com.core", "com.common", "com.service"})
public class TestConfig {
    // 필요한 추가적인 빈 설정

}