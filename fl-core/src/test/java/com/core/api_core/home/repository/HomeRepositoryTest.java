package com.core.api_core.home.repository;

import com.core.api_core.home.model.Home;
import com.core.config.TestConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static com.core.helper.HomeHelper.generateHome;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestConfig.class)
public class HomeRepositoryTest {

    @Autowired
    private HomeRepository homeRepository;

    @Test
    void 판매중인_집_게시글_모두조회(){
        //given
        for(int i=0; i<3; i++){
            homeRepository.save(generateHome());
        }
        //when
        List<Home> allSellHome = homeRepository.findAllSellHome();

        //then
        Assertions.assertThat(allSellHome.size()).isEqualTo(3);
    }

    @Test
    void 도시이름으로_집_게시글_모두조회() {
        //given
        for(int i=0; i<3; i++){
            homeRepository.save(generateHome());
        }
        List<Home> allFindHome = homeRepository.findByCity("city");
        //then
        Assertions.assertThat(allFindHome.size()).isEqualTo(3);
    }

    @Test
    void 사용자_id_로_해당되는_모든_집_게시글_조회(){
        //given
        for(int i=0; i<3; i++){
            homeRepository.save(generateHome());
        }
        //when
        List<Home> allFindHome = homeRepository.findByUserId(1L);

        //then
        Assertions.assertThat(allFindHome.size()).isEqualTo(3);
    }


}
