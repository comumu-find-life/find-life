package com.api.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.api","com.service", "com.core", "com.redis", "com.chatting", "com.common"})
public class ApiConfig {

}
