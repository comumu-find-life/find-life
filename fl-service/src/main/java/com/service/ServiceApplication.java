package com.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EntityScan(basePackages = {"com.core"})
@EnableJpaRepositories(basePackages = {"com.core"})
//@ComponentScan(basePackages = {"com.common", "com.core."})
public class ServiceApplication {
    //System.setProperty("spring.config.name", "application,application-db");

    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class, args);
    }
}
