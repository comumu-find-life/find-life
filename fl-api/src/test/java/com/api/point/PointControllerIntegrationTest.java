package com.api.point;

import com.api.config.TestConfig;
import com.core.api_core.user.repository.UserAccountRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static com.core.user.UserBuilder.createUser;

@SpringBootTest
@ContextConfiguration(classes = TestConfig.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class PointControllerIntegrationTest {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private EntityManager entityManager;

    private String token;

    @BeforeEach
    public void setUp() {
        token = "Bearer your-jwt-token";
    }

    @AfterEach
    public void tearDown() {
        userAccountRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "PROVIDER")
    public void 포인트_출금_신청_테스트(){

    }
}
