package com.core.home.repository;

import com.core.api_core.home.model.Home;
import com.core.api_core.home.repository.HomeRepository;
import com.core.api_core.user.model.User;
import com.core.api_core.user.repository.UserRepository;
import com.core.config.TestConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static com.core.helper.HomeHelper.generateHome;
import static com.core.helper.UserHelper.generateUser;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestConfig.class)
public class CustomHomeRepositoryImplTest {

    @Autowired
    private HomeRepository homeRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void home_페이징_조회_테스트() {
        //given
        User user = userRepository.save(generateUser(1L));
        Home save = homeRepository.save(generateHome(user.getId()));

        homeRepository.findHomeAndUserById(save.getId());
        System.out.println("--------");
    }

}
