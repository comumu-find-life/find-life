package com.api.config;

import com.api.security.CustomUserDetailsService;
import com.api.security.filter.CustomLoginAuthenticationFilter;
import com.api.security.filter.JwtAuthenticationFilter;
import com.api.security.handler.LoginFailureHandler;
import com.api.security.handler.LoginSuccessHandler;
import com.api.security.service.JwtService;
import com.core.api_core.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redis.user.service.UserRedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

import static com.api.config.AuthUrlPatterns.GET_AUTH_WHITELIST;
import static com.api.config.AuthUrlPatterns.POST_AUTH_WHITELIST;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ApiSecurityConfig {

    private final UserRedisService redisService;
    private final JwtService jwtService;
    private final ObjectMapper objectMapper;
    private final CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .formLogin(formLogin -> formLogin.disable())
                .httpBasic(httpBasic -> httpBasic.disable())
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers(HttpMethod.GET, GET_AUTH_WHITELIST).permitAll()
                        .requestMatchers(HttpMethod.POST, POST_AUTH_WHITELIST).permitAll()
                        .anyRequest().authenticated()
                );
      
        http.addFilterAfter(customJsonUsernamePasswordAuthenticationFilter(http), LogoutFilter.class);
        http.addFilterBefore(jwtAuthenticationProcessingFilter(), CustomLoginAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CustomLoginAuthenticationFilter customJsonUsernamePasswordAuthenticationFilter(HttpSecurity http) throws Exception {
        CustomLoginAuthenticationFilter customJsonUsernamePasswordLoginFilter = new CustomLoginAuthenticationFilter(objectMapper);
        customJsonUsernamePasswordLoginFilter.setAuthenticationManager(authenticationManager(http));
        customJsonUsernamePasswordLoginFilter.setAuthenticationSuccessHandler(loginSuccessHandler());
        customJsonUsernamePasswordLoginFilter.setAuthenticationFailureHandler(loginFailureHandler());
        return customJsonUsernamePasswordLoginFilter;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();

    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationProcessingFilter() {
        return new JwtAuthenticationFilter(jwtService, redisService);
    }

    @Bean
    public LoginSuccessHandler loginSuccessHandler() {
        return new LoginSuccessHandler(jwtService, redisService);
    }

    @Bean
    public LoginFailureHandler loginFailureHandler() {
        return new LoginFailureHandler();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}