package com.chatting.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.api","com.service","com.authority", "com.core"})
public class ChattingConfig {
}
