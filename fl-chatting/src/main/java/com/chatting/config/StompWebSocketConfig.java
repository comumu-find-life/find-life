package com.chatting.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;


@EnableWebSocketMessageBroker // 웹 소켓 메시지 브로커 활성에
@Configuration
public class StompWebSocketConfig implements WebSocketMessageBrokerConfigurer {


    @Value("${domain.base}")
    private String baseUrl;

    // /dm
    @Value("${domain.chat.directMessage}")
    private String dmUrl;

    // /pub
    @Value("${domain.chat.publish}")
    private String pub;

    // /sub
    @Value("${domain.chat.subscribe}")
    private String sub;



    // dmUrl 엔드포인트 등록 메서드
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint(dmUrl)
                .setAllowedOrigins(baseUrl).withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes(pub);
        registry.enableSimpleBroker(sub);
    }
}

