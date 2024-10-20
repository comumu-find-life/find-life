package com.service.home;

import com.core.api_core.home.repository.HomeRepository;
import com.core.api_core.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

public class HomeServiceTest {

    @MockBean
    private HomeRepository homeRepository;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private HomeService homeService;


    @Test
    void test(){

    }
}