package com.api.config;

import com.api.security.filter.CustomLoginAuthenticationFilter;
import com.api.security.filter.JwtAuthenticationFilter;
import com.api.security.handler.LoginFailureHandler;
import com.api.security.handler.LoginSuccessHandler;
import com.api.security.service.JwtService;
import com.api.security.service.LoginService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redis.user.UserRedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 인증은 CustomJsonUsernamePasswordAuthenticationFilter 에서 authenticate()로 인증된 사용자로 처리
 * JwtAuthenticationProcessingFilter 는 AccessToken, RefreshToken 재발급
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private static final String[] GET_AUTH_WHITELIST = {
            "/v1/api/home",
            "/v1/api/home/**",
            "/v1/api/homes",
            "/v1/api/homes/{city}",
            "/v1/api/homes/**",
            "/v1/api/dm",
            "/v1/api/dm/**",
            "/v1/api/homes/overview",
            "/v1/api/user/profile",
    };

    private static final String[] POST_AUTH_WHITELIST = {
            "/v1/api/user",
            "/v1/api/user/sign-up",
    };

    private final LoginService loginService;
    private final UserRedisService redisService;
    private final JwtService jwtService;
    private final ObjectMapper objectMapper;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .formLogin(formLogin -> formLogin.disable())
                .httpBasic(httpBasic -> httpBasic.disable())
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers.frameOptions().disable())
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.GET, GET_AUTH_WHITELIST).permitAll()
                        .requestMatchers(HttpMethod.POST, POST_AUTH_WHITELIST).permitAll()
                        .anyRequest().authenticated()
                );

        http.addFilterAfter(customJsonUsernamePasswordAuthenticationFilter(), LogoutFilter.class);
        http.addFilterBefore(jwtAuthenticationProcessingFilter(), CustomLoginAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CustomLoginAuthenticationFilter customJsonUsernamePasswordAuthenticationFilter() {
        CustomLoginAuthenticationFilter customJsonUsernamePasswordLoginFilter = new CustomLoginAuthenticationFilter(objectMapper);
        customJsonUsernamePasswordLoginFilter.setAuthenticationManager(authenticationManager());
        customJsonUsernamePasswordLoginFilter.setAuthenticationSuccessHandler(loginSuccessHandler());
        customJsonUsernamePasswordLoginFilter.setAuthenticationFailureHandler(loginFailureHandler());
        return customJsonUsernamePasswordLoginFilter;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(loginService);
        return new ProviderManager(provider);
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

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://10.0.2.2:8080")
                        .allowedMethods("*")
                        .allowedHeaders("*");
            }
        };
    }
}