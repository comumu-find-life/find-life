package com.authority.config;

import com.authority.filter.CustomLoginAuthenticationFilter;
import com.authority.filter.JwtAuthenticationFilter;
import com.authority.handler.LoginFailureHandler;
import com.authority.handler.LoginSuccessHandler;
import com.authority.service.JwtService;
import com.authority.service.LoginService;
import com.core.user.repository.UserRepository;
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

/**
 * 인증은 CustomJsonUsernamePasswordAuthenticationFilter 에서 authenticate()로 인증된 사용자로 처리
 * JwtAuthenticationProcessingFilter 는 AccessToken, RefreshToken 재발급
 */
@Configuration
@EnableWebSecurity // @EnableWebSecurity 어노테이션을 붙여야 Spring Security 기능을 사용할 수 있다.
@RequiredArgsConstructor
public class SecurityConfig {

    private static final String[] GET_AUTH_WHITELIST = {
            "/v1/api/home",
            "/v1/api/homes",
    };

    private static final String[] POST_AUTH_WHITELIST = {
            "/v1/api/user",
    };
    private final LoginService loginService;
    private final UserRedisService redisService;
    private final JwtService jwtService;
    private final ObjectMapper objectMapper;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .formLogin((formLogin) -> formLogin.disable()) // Spring Security 에서는 아무 설정을 하지 않으면 기본 FormLogin 형식의 로그인을 제공한다
                .httpBasic((httpBasic) -> httpBasic.disable()) // JWT 토큰을 사용한 로그인 방식이기 때문에 httpBasic disable 로 설정
                .csrf((csrfConfig) -> csrfConfig.disable()) // 서버에 인증 정보를 저장하지 않고, 요청 시 인증 정보를 담아서 요청 하므로 보안 기능인 csrf 불필요
                .httpBasic((httpBasic) -> httpBasic.disable())
                .headers((headerConfig) ->
                        headerConfig.frameOptions(frameOptionsConfig ->
                                frameOptionsConfig.disable()
                        )
                )
                // 세션 사용 x
                .sessionManagement((sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

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
        CustomLoginAuthenticationFilter customJsonUsernamePasswordLoginFilter
                = new CustomLoginAuthenticationFilter(objectMapper);

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
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtService, redisService);
        return jwtAuthenticationFilter;
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