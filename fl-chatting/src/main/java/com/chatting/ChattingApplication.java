package com.chatting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class ChattingApplication {
    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("Australia/Sydney"));
        SpringApplication.run(ChattingApplication.class, args);
    }
}
