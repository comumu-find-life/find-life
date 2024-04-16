package com.service.home.mapper;

import com.core.home.model.Home;
import com.core.home.model.HomeAddress;
import com.core.home.model.HomeImage;
import com.service.home.dto.HomeAddressDto;
import com.service.home.dto.HomeDto;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class HomeMapperTest {

    private HomeMapper homeMapper = HomeMapper.INSTANCE;

    @Test
    void Home_to_HomeDto_변경_테스트() {
        //given
        HomeAddress homeAddress = HomeAddress.builder()
                .city("city name")
                .id(1L)
                .build();

        List<HomeImage> images = new ArrayList<>();
        images.add(HomeImage.builder()
                        .imageUrl("url1")
                .build());

        Home homeEntity = Home.builder()
                .id(1L)
                .images(images)
                .userId(2L)
                .homeAddress(homeAddress)
                .bill(30000)
                .build();

        //when
        HomeDto homeDto = homeMapper.toDto(homeEntity);

        //then
        assertThat(homeDto.getHomeAddressDto().getCity()).isEqualTo("city name");
        assertThat(homeDto.getImages().size()).isEqualTo(1);
    }

    @Test
    void HomeDto_to_Home_엔티티_변경_테스트() {
        //given
        HomeAddressDto addressDto = HomeAddressDto.builder()
                .city("test city")
                .postCode(1234)
                .build();

        List<String> images = new ArrayList<>();

        images.add("url1");
        images.add("url2");
        images.add("url3");

        HomeDto homeDto = HomeDto.builder()
                .homeAddressDto(addressDto)
                .userId(1L)
                .images(images)
                .bill(20000)
                .bond(10000)
                .build();

        //when
        Home entity = homeMapper.toEntity(homeDto);

        //then

        assertThat(entity.getImages().size()).isEqualTo(3);
        assertThat(entity.getHomeAddress().getCity()).isEqualTo("test city");
        assertThat(entity.getUserId()).isEqualTo(1L);
    }


}
