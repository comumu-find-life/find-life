package com.core.api_core.chat;

import com.core.api_core.chat.model.DirectMessageRoom;
import com.core.api_core.chat.repository.DirectMessageRoomRepository;
import com.core.api_core.user.model.User;
import com.core.api_core.user.repository.UserRepository;
import com.core.config.TestConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

import static com.core.helper.DirectMessageRoomHelper.generateDirectMessageRoom;
import static com.core.helper.UserHelper.generateUser;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestConfig.class)
public class DirectMessageRoomRepositoryTest {

    @Autowired
    private DirectMessageRoomRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void 두명의_사용자가_속한_채팅방_조회_테스트() {
        //given
        User user1 = userRepository.save(generateUser(1L));
        User user2 = userRepository.save(generateUser(2L));
        DirectMessageRoom directMessageRoom = generateDirectMessageRoom(user1, user2);
        repository.save(directMessageRoom);

        //when
        Optional<DirectMessageRoom> byUser1IdAndUser2Id = repository.findByUser1IdAndUser2Id(1L, 2L);
        //then
        Assertions.assertThat(byUser1IdAndUser2Id).isNotEmpty();
    }

    @Test
    void 자신이_소속한_채팅방_조회_테스트() {
        //given
        saveMultipleDirectMessageRoom();

        //when
        List<DirectMessageRoom> byUser1IdOrUser2Id = repository.findByUser1IdOrUser2Id(1L);

        //then
        Assertions.assertThat(byUser1IdOrUser2Id.size()).isEqualTo(2);

    }

    private void saveMultipleDirectMessageRoom() {
        User user1 = userRepository.save(generateUser(1L));
        User user2 = userRepository.save(generateUser(2L));
        DirectMessageRoom directMessageRoom = generateDirectMessageRoom(user1, user2);
        repository.save(directMessageRoom);

        User user4 = userRepository.save(generateUser(4L));
        DirectMessageRoom directMessageRoom2 = generateDirectMessageRoom(user1, user4);
        repository.save(directMessageRoom2);

    }
}
