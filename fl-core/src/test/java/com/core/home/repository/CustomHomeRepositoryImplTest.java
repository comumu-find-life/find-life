package com.core.home.repository;

import com.core.config.TestConfig;
import com.core.home.model.Home;
import com.core.home.model.HomeAddress;
import com.core.home.reposiotry.HomeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

@DataJpaTest // JPA 컴포넌트들만을 위한 테스트 애노테이션이다. (JPA에 필요한 설정들에 대해서만 Bean을 등록한다.)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 테스트용 DB 설정 애노테이션
@Import(TestConfig.class)
public class CustomHomeRepositoryImplTest {

    @Autowired
    private HomeRepository homeRepository;

    @BeforeEach
    void setUp(){
        HomeAddress homeAddress = HomeAddress.builder()
                .city("cityName")
                .streetNumber("100")
                .streetName("streetName")
                .build();

        Home home = Home.builder()
                .peopleCount(5)
                .homeAddress(homeAddress)
                .build();

        homeRepository.save(home);
    }

    @Test
    void city_이름으로_home_조회기능_테스트(){
        //given
        String cityName = "ci";
        //when
        List<Home> byCity = homeRepository.findByCity(cityName);
        //then
        Assertions.assertThat(byCity.get(0).getHomeAddress().getCity()).isEqualTo("cityName");
    }

}
