package com.api.config;

import com.api.security.service.JwtService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.SecurityFilterChain;


@TestConfiguration
@Configuration
@ComponentScan(basePackages = {"com.api", "com.core", "com.common", "com.service"})
public class TestConfig {
    @MockBean
    private SecurityFilterChain securityFilterChain;

    @MockBean
    private JwtService jwtService;
}