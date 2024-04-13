package com.core.user.repository;

import com.core.user.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByEmail() {
        // Given
        User user = User.builder()
                .email("testemail@example.com")
                .nickName("testNickname")
                .build();
        userRepository.save(user);

        // When
        Optional<User> foundUserOptional = userRepository.findByEmail("testemail@example.com");

        // Then
        assertTrue(foundUserOptional.isPresent());
        User foundUser = foundUserOptional.get();
        assertEquals("testemail@example.com", foundUser.getEmail());
        assertEquals("testNickname", foundUser.getNickName());
    }

    // Add more test cases for other methods if needed
}