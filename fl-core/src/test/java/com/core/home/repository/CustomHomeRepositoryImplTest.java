package com.core.home.repository;

import com.core.api_core.home.model.Home;
import com.core.api_core.home.model.HomeAddress;
import com.core.api_core.home.repository.HomeRepository;
import com.core.config.TestConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

@DataJpaTest // JPA 컴포넌트들만을 위한 테스트 애노테이션이다. (JPA에 필요한 설정들에 대해서만 Bean을 등록한다.)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 테스트용 DB 설정 애노테이션
@Import(TestConfig.class)
public class CustomHomeRepositoryImplTest {

    @Autowired
    private HomeRepository homeRepository;



    @Test
    void home_페이징_조회_테스트() {
        //given
        PageRequest pageable = PageRequest.of(0, 3);
        //when
        Page<Home> all = homeRepository.findAll(pageable);
        //then
        Assertions.assertThat(all.getSize()).isEqualTo(3);

    }

}
