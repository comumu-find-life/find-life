//package com.core.chat_core.chat.repository;
//
//
//import com.core.api_core.chat.model.DirectMessage;
//import com.core.config.TestConfig;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.context.annotation.Import;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//
//import static com.core.helper.DirectMessageHelper.generateDirectMessage;
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Import(TestConfig.class)
//public class DirectMessageRepositoryTest {
//
//    @Autowired
//    private DirectMessageRepository repository;
//
//    @Test
//    void 두명의_사용자_사이의_대화중_마지막_메시지_조회_테스트(){
//        //given
//        repository.save(generateDirectMessage(LocalDateTime.of(2024,9,15,15,30,1)));
//        repository.save(generateDirectMessage(LocalDateTime.of(2024,9,15,15,31,1)));
//        DirectMessage save = repository.save(generateDirectMessage(LocalDateTime.of(2024, 9, 15, 15, 32, 1)));
//
//        //when
//        Optional<DirectMessage> lastMessageMyUserIds = repository.findLastMessageMyUserIds(1L, 2L);
//
//        //then
//        Assertions.assertThat(lastMessageMyUserIds.get().getSentAt()).isEqualTo(save.getSentAt());
//    }
//
//    @Test
//    void 채팅방에_속해있는_모든_채팅정보_조회_테스트(){
//        //given
//        repository.save(generateDirectMessage(LocalDateTime.of(2024,9,15,15,30,1)));
//        repository.save(generateDirectMessage(LocalDateTime.of(2024,9,15,15,31,1)));
//        repository.save(generateDirectMessage(LocalDateTime.of(2024, 9, 15, 15, 32, 1)));
//
//        //when
//        List<DirectMessage> directMessageByUserIds = repository.findDirectMessageByUserIds(1L, 2L);
//
//        Assertions.assertThat(directMessageByUserIds.size()).isEqualTo(3);
//    }
//
//}
